import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기 
 * @author 김상진 
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
		// 완성하시오.
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
		// 완성하시오.
	}
	
	public void deleteMessage(int index) {
		// 완성하시오.
	}
}
