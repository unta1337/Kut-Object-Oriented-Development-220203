import java.util.HashSet;
import java.util.Set;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습 
 * @version 2022년도 2학기
 * @author 김상진, 김성녕
 * @file Restriction.java
 * 첨가물에 적용되는 제한 사항을 유지하기 위한 클래스
 */
public class Restriction {
    public int maxAddition = 0;
    public Set<String> exclusionList = new HashSet<>();
}
