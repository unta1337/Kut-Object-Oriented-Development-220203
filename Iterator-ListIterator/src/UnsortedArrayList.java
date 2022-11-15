import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김상진 
 * 배열 기반 비정렬 범용 리스트
 */
public class UnsortedArrayList<T> implements Iterable<T> {
	private int capacity = 5;
	@SuppressWarnings("unchecked")
	private T[] items = (T[])(new Object[capacity]);
	private int size = 0;
	
	private class ArrayListIterator implements ListIterator<T>{
		/**
		 * 다음에 반환할 요소의 인덱스
		 * (cursor는 다음 요소를 가리키고 있다. 즉, next()는 cursor가 가리키는 요소를 반환한다. prev()는 cursor - 1이 가리키는 요소를 반환한다.)
		 */
		private int cursor = 0;
		/**
		 * 이전에 반환한 요소의 인덱스
		 * (-1이면 아직 이전 cursor가 정의되지 않았거나 add()또는 remove()를 호출하여 이전 반환 요소가 모호해진 상태.)
		 */
		private int prevCursor = -1;
		@Override public boolean hasNext() {
			// cursor는 배열의 인덱스이므로 [0, size) 범위에 있어야 한다.
			// 그런데 next()는 다음 요소만을 반환하므로 하한은 고려할 필요가 없다.
			return cursor < size;
		}
		@Override public T next() {
			// 다음 요소가 없으면 예외 발생.
			if (!hasNext())
				throw new NoSuchElementException();

			// 이전 cursor 갱신.
			prevCursor = cursor;

			// 다음 요소 반환.
			return items[cursor++];
		}
		@Override public boolean hasPrevious() {
			// prevCursor는 cursor - 1이므로 (0, size] 범위에 있어야 한다.
			// 그런데 prev()는 이전 요소만을 반환하므로 상한은 고려할 필요가 없다.
			return cursor > 0;
		}
		@Override public T previous() {
			// 이전 요소가 없으면 예외 발생.
			if (!hasPrevious())
				throw new NoSuchElementException();

			// 이전 cursor 갱신.
			prevCursor = cursor - 1;

			// 이전 요소 반환.
			return items[--cursor];
		}
		@Override public int nextIndex() {
			return cursor;
		}
		@Override public int previousIndex() {
			return cursor - 1;
		}
		@Override public void remove() {
			// 이전 반환 요소가 모호하면 예외 발생.
			if (prevCursor == -1)
				throw new IllegalStateException();

			// 이전 반환 요소를 삭제.
			for (int i = prevCursor; i < size - 1; i++)
				items[i] = items[i + 1];

			// 필요 변수 갱신.
			size--;
			cursor = prevCursor;
			prevCursor = -1;
		}
		@Override public void set(T e) {
			// 이전 반환 요소가 모호하면 예외 발생.
			if (prevCursor == -1)
				throw new IllegalStateException();

			// 이전 반환 요소를 e로 변경.
			items[prevCursor] = e;
		}
		@Override public void add(T e) {
			// 배열이 꽉 차면 확장.
			if (size == capacity)
				increaseCapacity();

			// 새 요소를 추가.
			for (int i = size; i > cursor; i--)
				items[i] = items[i - 1];
			items[cursor] = e;

			// 필요 변수 갱신.
			size++;
			cursor++;
			prevCursor = -1;
		}
	}
	
	public boolean isFull(){
		return false;
	}
	public boolean isEmpty(){
		return size==0;
	}
	public int size() {
		return size;
	}
	
	public T peekBack() {
		if(isEmpty()) throw new IllegalStateException();
		return items[size-1];
	}
	
	private void increaseCapacity() {
		capacity *= 2;
		items = Arrays.copyOf(items, capacity);
	}
	
	public void pushBack(T item){
		if(size==capacity) increaseCapacity();
		items[size++] = item;
	}
	
	public T popBack() {
		if(isEmpty()) throw new IllegalStateException();
		return items[--size];
	}
	
	public T get(int index){
		if(index<0 || index>=size) throw new IndexOutOfBoundsException("유효하지 않은 색인 사용");
		return items[index];
	}
	
	public void remove(T item) {
		for(int i=0; i<size; i++)
			if(items[i].equals(item)) {
				for(int j=i+1; j<size; j++)
					items[j-1] = items[j];
				--size;
				break;
			}
	}
	
	@SuppressWarnings("unchecked")
	public void pushBackAll(T... items) {
		for(var item: items) pushBack(item);
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ArrayListIterator();
	}
	
	public ListIterator<T> listIterator() {
		return new ArrayListIterator();
	}
}
