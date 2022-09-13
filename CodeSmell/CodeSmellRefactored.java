package CodeSmell;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Optional;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김상진
 * 0,1,2,3으로 표시되는 맵이 주어짐
 * 0: 통로
 * 1: 시작위치
 * 2: 보석
 * 3: 벽
 * 목적: 시작위치에서 보석까지의 최단 경로 찾기 
 * 이동은 상하좌우로만 할 수 있음
 * 맵에는 항상 보석이 하나 주어짐
 * 보석을 찾을 수 없으면 최단 경로의 길이는 -1을 출력해야 함
 * 주어진 해결책에서 코드 냄세를 찾아 리펙토링하세요.
 * 요구사항. 주석에 다음을 포함하여 주세요.
 * 1) 리펙토링한 순서
 * 2) 각 코드 냄새를 제거하기 위해 리펙토링한 방법
 */
public class CodeSmellRefactored {
	public static int solve(int[][] map) {
		Optional<Status> startPos = Optional.ofNullable(getStartingStatus(map));

		return startPos.map(pos -> getMinLength(map, pos)).orElse(-1);
	}

	public static Status getStartingStatus(int[][] map) {
		int row, col;
		for (row = 0; row < map.length; row++)
			for (col = 0; col < map[0].length; col++)
				if (map[row][col] == 1)
					return new Status(row, col, 0);

		return null;
	}

	public static int getMinLength(int[][] map, Status startPos) {
		Queue<Status> queue = new ArrayDeque<>();
		queue.add(startPos);

		boolean visited[][] = new boolean[map.length][map[0].length];

		while (!queue.isEmpty()) {
			Status current = queue.poll();

			int row = current.row;
			int col = current.col;
			int length = current.length;

			if (!(0 <= row && row < map.length) || !(0 <= col && col < map[0].length))
				continue;

			if (visited[row][col])
				continue;
			visited[row][col] = true;

			switch (map[row][col]) {
				case 2:
					return length;
				case 3:
					continue;
			}

			queue.add(new Status(row + 1, col, length + 1));
			queue.add(new Status(row - 1, col, length + 1));
			queue.add(new Status(row, col + 1, length + 1));
			queue.add(new Status(row, col - 1, length + 1));
		}

		return -1;
	}

	public static void main(String[] args) {
		int[][] map = {
			{3,0,3,0,3,1,3},
			{3,0,0,0,3,0,3},
			{3,0,3,0,0,0,3},
			{3,0,3,3,3,0,3},
			{3,0,0,2,3,0,0},
			{3,3,3,3,3,3,3}
		};
		System.out.println(solve(map));
		map = new int[][]{
			{3,3,3,0,3,0,3},
			{3,0,0,0,3,0,3},
			{1,0,3,0,3,0,3},
			{3,0,3,3,3,0,3},
			{3,0,3,2,0,0,0},
			{3,3,3,3,3,3,3}
		};
		System.out.println(solve(map));
		map = new int[][]{
			{3,3,3,0,3,3,3},
			{3,0,0,0,3,2,3},
			{1,0,3,0,0,0,3},
			{3,0,3,3,3,0,3}
		};
		System.out.println(solve(map));
	}

	record Status(int row, int col, int length) {
	}
}