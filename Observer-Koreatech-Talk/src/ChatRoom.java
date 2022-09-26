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
		// 완성하시오.
	}
}
