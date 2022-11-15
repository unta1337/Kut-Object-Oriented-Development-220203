import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2021년도 2학기
 * @author 김상진 
 * 이중 연결구조 기반 비정렬 범용 리스트
 */
public class UnsortedDoubleLinkedList<T> implements Iterable<T> {
	private static class Node<T>{
		private T item = null;
		private Node<T> prev = null;
		private Node<T> next = null;
		public Node(T item) {
			this.item = item;
		}
	}
	private class LinkedListIterator implements ListIterator<T>{
		@SuppressWarnings("unused")
		/**
		 * 반환할 요소.
		 */
		private Node<T> curr = head;
		/**
		 * 이전에 반환한 요소.
		 */
		private Node<T> prev = null;
		/**
		 * 반환할 요소 인덱스.
		 */
		private int index = 0;
		/**
		 * 이전에 반환한 요소 인덱스.
		 */
		private int prevIndex = -1;
		@Override
		public boolean hasNext() {
			// tail을 넘어 null을 가리키면 다음 요소가 없음.
			return curr != null;
		}
		@Override
		public T next() {
			// 다음 요소가 없으면 예외 발생.
			if (!hasNext())
				throw new NoSuchElementException();

			// 이전 반환에 대한 변수 갱신.
			prevIndex = index;
			index++;

			// 이전 반환에 대한 변수를 갱신함과 동시에 현재 반환할 요소를 prev에 유지.
			prev = curr;
			curr = curr.next;

			return prev.item;
		}
		@Override
		public boolean hasPrevious() {
			// curr가 처음 요소가 아니면 이전 요소가 있다고 판단.
			return curr != head;
		}
		@Override
		public T previous() {
			// 이전 요소가 없으면 예외 발생.
			if (!hasPrevious())
				throw new NoSuchElementException();

			// 이전 반환에 대한 변수 갱신.
			prevIndex = index - 1;
			index--;

			// 이전 반환에 대한 변수를 갱신함과 동시에 현재 반환할 요소를 curr에 유지.
			prev = curr.prev;
			curr = curr.prev;

			return curr.item;
		}
		@Override
		public int nextIndex() {
			return index;
		}
		@Override
		public int previousIndex() {
			return index - 1;
		}
		@Override
		public void remove() {
			// 이전 반환 요소가 모호하면 예외 발생.
			if (prev == null)
				throw new IllegalStateException();

			// 이전 반환 요소가 head인 경우를 방지하기 위해 조건문 설정.
			// prev 요소를 제거하기 위해 prev로 연결되는 노드 재조정.
			if (prev.prev != null)
				prev.prev.next = prev.next;
			if (prev.next != null)
				prev.next.prev = prev.prev;

			// curr 요소 갱신.
			curr = prev.next;
			// head를 제거한 경우 head를 갱신.
			if (prev == head)
				head = curr;

			// 필요 변수 갱신.
			size--;
			index = prevIndex;
			prev = null;
			prevIndex = -1;
		}
		@Override
		public void set(T e) {
			// 이전 반환 요소가 모호하면 예외 발생.
			if (prev == null)
				throw new IllegalStateException();

			// 이전 반환 요소의 값을 e로 갱신.
			prev.item = e;
		}
		@Override
		public void add(T e) {
			Node<T> insertNode = new Node<T>(e);

			// 마지막 요소 이후에 추가하는 경우.
			if (!hasNext()) {
				curr = new Node<>(null);
				curr.prev = tail;
				tail.next = curr;
				tail = curr;
			}

			// 첫 요소 이전에 추가하는 경우.
			if (hasPrevious())
				curr.prev.next = insertNode;

			// insertNode 삽입.
			insertNode.prev = curr.prev;
			insertNode.next = curr;
			curr.prev = insertNode;

			// 마지막 요소 이후에 추가하는 경우.
			if (curr == tail) {
				insertNode.next = null;
				curr = null;
			}

			// 필요 변수 갱신.
			size++;
			index++;
			prev = null;
			prevIndex = -1;
		}		
	}
	
	private Node<T> head = null;
	private Node<T> tail = null;
	private int size = 0;
	
	public boolean isFull() {
		return false;
	}
	public boolean isEmpty() {
		return size==0;
	}
	public int size() {
		return size;
	}
	
	public T get(int index) {
		if(index<0 || index>=size) throw new IndexOutOfBoundsException();
		Node<T> curr = head;
		for(int i=0; i<index; i++) {
			curr = curr.next;
		}
		return curr.item;
	}

	public void pushFront(T item) {
		Node<T> newNode = new Node<>(item);
		newNode.next = head;
		if(head!=null) head.prev = newNode;
		else tail = newNode;
		head = newNode;
		++size;
	}
	
	public T popFront() {
		if(isEmpty()) throw new IllegalStateException();
		Node<T> popNode = head;
		head = head.next;
		if(head!=null) head.prev = null;
		else tail = null;
		--size;
		return popNode.item;
	}
	
	public T peekFront() {
		if(isEmpty()) throw new IllegalStateException();
		return head.item;
	}
	
	public void pushBack(T item) {
		Node<T> newNode = new Node<>(item);
		if(tail==null) head = tail = newNode;
		else{
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		++size;
	}
	
	public T popBack() {
		if(isEmpty()) throw new IllegalStateException();
		Node<T> popNode = tail;
		tail = tail.prev;
		if(tail!=null) tail.next = null;
		else head = null;
		--size;
		return popNode.item;
	}
	
	public T peekBack() {
		if(isEmpty()) throw new IllegalStateException();
		return tail.item;
	}
	
	@SuppressWarnings("unchecked")
	public void pushBackAll(T... items) {
		for(var item: items) pushBack(item);
	}
	
	public boolean find(T item) {
		if(isEmpty()) return false;
		Node<T> curr = head;
		while(curr!=null) {
			if(curr.item.equals(item)) return true;
			curr = curr.next;
		}
		return false;
	}
	
	public void removeFirst(T item) {
		if(isEmpty()) return;
		Node<T> dummy = new Node<>(null);
		dummy.next = head;
		head.prev = dummy;
		Node<T> curr = head;
		while(curr!=null) {
			if(curr.item.equals(item)) {
				curr.prev.next = curr.next;
				if(curr.next!=null) curr.next.prev = curr.prev;
				if(curr==tail) tail = tail.prev;
				--size;
				break;
			}
			else curr = curr.next;
		}
		head = dummy.next;
		if(head==null) tail = null;
		else head.prev = null;
	}
	
	@Override public Iterator<T> iterator() {
		return new LinkedListIterator();
	}
	
	public ListIterator<T> listIterator() {
		return new LinkedListIterator();
	}
}
