import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기 
 * @author 김상진, 김성녕
 * @file ChatRoom.java
 * 관찰자 패턴: subject
 * 사용자 목록과 채팅 메시지 목록 유지
 * 채팅룸 목록, 사용자 목록 유지
 */
public class ChatRoom{
	private String roomName;
	private List<ChatMessage> roomLog = new ArrayList<>();
	// Map<사용자ID, 마지막 받은 메시지 색인>
	// 관찰자 목록
	private Map<String, Integer> userList = new HashMap<>();
	
	public ChatRoom(String name) {
		roomName = Objects.requireNonNull(name);
	}
	public String getRoomName() {
		return roomName;
	}
	
	// 관찰자 추가
	public boolean addUser(String userID) {
		// 이미 존재하는 사용자이면 다시 추가할 필요가 없음.
		// 새로 추가하는 상황이 아니므로 false.
		if (userList.containsKey(userID))
			return false;

		// 새로이 추가되는 사용자는 받은 메시지가 아무 것도 없으므로 -1로 초기화.
		userList.put(userID, -1);

		return true;
	}
	// 관찰자 삭제
	public boolean deleteUser(String userID) {
		if(!userList.containsKey(Objects.requireNonNull(userID)))
			return false;
		userList.remove(userID);
		return true;
	}
	
	public void newMessage(ChatMessage message) {
		if(!userList.containsKey(Objects.requireNonNull(message).getSenderID()))
			throw new IllegalArgumentException("존재하지 않는 전송자");
		message.setIndex(roomLog.size());
		roomLog.add(message);
		updateUsers();
	}
	
	// 관찰자 패턴에서 notifyObservers에 해당
	public void updateUsers() {
		userList.keySet().forEach(key->updateUser(key));
		//for(var key: userList.keySet()) updateUser(key);
	}
	public void updateUser(String userID) {
		// 요청한 사용자를 찾아 해당 유저가 없으면 예외 발생.
		// findUser를 이후에 사용하므로 userList를 이용하지 않고 검사.
		User findUser = ChatServer.getServer().getUsers().stream().filter(user -> user.getUserID() == userID)
		.findFirst().orElseThrow(() -> { throw new IllegalArgumentException("유효하지 않은 사용자"); });

		// 요청한 사용자가 오프라인이면 추가 작업을 하지 않음.
		if (!findUser.isOnline())
			return;

		// 사용자가 마지막으로 확인한 메시지 이후의 메시지 갱신.
		int lastIndex = userList.get(userID);
		roomLog.subList(lastIndex + 1, roomLog.size())
		.forEach(log -> { if (!log.isDeleted()) ChatServer.getServer().forwardMessage(userID, roomName, log); });	// 삭제된 메시지는 갱신하지 않음.

		// 마지막으로 확인한 메시지 인덱스 갱신.
		userList.put(userID, roomLog.size() - 1);
	}
	
	public void deleteMessage(int index) {
		// 채팅방에서는 정보의 유지 및 인덱스 일관성을 위해 해당하는 인덱스의 삭제 플래그만 갱신.
		// 채팅방의 메시지를 삭제하지 않으므로 유저의 메시지 로그를 채팅방 로그 인덱스 기준으로 한다면, 인덱스에 대해 신경쓰지 않아도 됨.
		roomLog.get(index).setDeleteFlag();
	}
}

/*
 * 1. 본 실습의 관찰자 패턴은 push 방식인가 pull 방식인가?
 * 	   본 실습에서 ChatRoom과 관계를 맺고 있는 클래스는 아래와 같다.
 *
 *     관찰자         관찰 대상     관련 메소드
 *     ChatWindow     User         update, updateChatArea
 *
 *     ChatWindow의 update 메소드는 roomName만을 받아 역할을 수행한다.
 *     이때 ChatWindow와 관계를 맺은 User에 대한 정보가 없으므로 pull 방식이라고 생각해볼 수 있다.
 *     실제로 update와 연결된 메소드 updateChatArea를 살펴보면 ChatWindow가 유지하고 있는 user 변수를 통해 User 클래스에 접근하여 필요한 변수를 취하는 것을 볼 수 있다.
 *     따라서 이는 pull 방식이다.
 * 
 * 2. 관찰 대상이 복수일 때 관찰자가 관찰 대상을 어떻게 구분하는가?
 *     본 실습에서 User는 roomLogs를 해시맵으로 저장하여 roomName 또는 채팅방의 이름 이용해 구분한다.
 *     즉 다수의 관찰 대상에 대한 각각의 고유 번호 등을 부여하여 이들을 구분할 수 있다.
 * 
 * 3. 본 실습에서 두 객체 외의 관찰자-관찰 대상으로 모델링된 것은 무엇인가?
 *     ChatRoom-User 관계 외에 관찰자-관찰 대상으로 모델링된 클래스는 아래와 같다.
 * 
 *     관찰자         관찰 대상     관련 메소드
 *     User           ChatRoom     update
 * 
 *     이때 User의 update 메소드는 인자로 roomName과 message를 취한다.
 *     User와 관계를 맺은 ChatRoom에 대한 정보를 전달하여 pull 방식 같아 보일 수 있다.
 *     하지만 해당 변수는 User가 유지하는 roomLogs 리스트에서 특정 채팅방을 탐색하는 용도로 사용된다.
 *     즉 roomName을 통해 ChatRoom 클래스로부터 불러오는 정보가 없고, message만을 통해 User가 유지하는 roomLogs를 갱신하므로 이는 push 방식이다.
 * 
 *     이 밖에도 JavaFX의 이벤트 핸들러 등이 관찰자-관찰 대상 관계로 모델링된다.
 */