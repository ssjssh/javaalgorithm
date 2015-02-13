package ssj.algorithm.collections;

import com.google.common.base.Preconditions;
import ssj.algorithm.SearchTree;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Created by shenshijun on 15/2/5.
 */
public class AVLTree<T extends Comparable<T>> implements SearchTree<T> {
    private Node _head;
    private int _size;

    public AVLTree() {
        _head = new Node(null, null, null);
        _size = 0;
    }

    /**
     * 使用一个排好序的列表来创建AVL树
     *
     * @param arr
     */
    public AVLTree(T[] arr) {
        Preconditions.checkNotNull(arr);
        _head = createMinimalAVL(arr, 0, arr.length-1);
        _size = arr.length;
    }

    private Node createMinimalAVL(T[] arr, int start, int end) {
        if (end < start) {
            return null;
        }

        int middle = (end + start) / 2;
        Node root = new Node(null, null, null, arr[middle]);
        Node left = createMinimalAVL(arr, start, middle - 1);
        Node right = createMinimalAVL(arr, middle + 1, end);
        root.setLeft(left);
        root.setRight(right);
        if (left != null) {
            left.setParent(root);
        }

        if (right != null) {
            right.setParent(root);
        }

        root.fixupHeight();
        return root;
    }

    @Override
    public void add(T ele) {
        Preconditions.checkNotNull(ele);
        _size++;
        if (_head.getValue() == null) {
            _head.setValue(ele);
            return;
        }
        Node cur_node = _head;
        Node parent_node = cur_node;
        Node new_node = new Node(null, null, null, ele);
        while (cur_node != null) {
            cur_node.incHeight();
            parent_node = cur_node;
            if (cur_node.compareTo(new_node) >= 0) {
                cur_node = cur_node.getLeft();
            } else {
                cur_node = cur_node.getRight();
            }
        }

        if (parent_node.compareTo(new_node) < 0) {
            parent_node.setRight(new_node);
        } else {
            parent_node.setLeft(new_node);
        }
        new_node.parent = parent_node;
        fixUp(new_node);
    }

    private void fixUp(Node start) {
        Node cur_node = start;
        while (cur_node != null) {
            if (Math.abs(leftHeight(cur_node) - rightHeight(cur_node)) >= 2) {
                Node left_node = cur_node.getLeft();
                Node right_node = cur_node.getRight();
                if (rightHeight(cur_node) > leftHeight(cur_node)) {
                    if (leftHeight(right_node) > rightHeight(right_node)) {
                        right_node.getLeft().rightRotate();
                        right_node = right_node.getParent();
                    }
                    right_node.leftRotate();
                    cur_node = right_node;
                } else {
                    if (rightHeight(left_node) > leftHeight(left_node)) {
                        left_node.getRight().leftRotate();
                        left_node = left_node.getParent();
                    }
                    left_node.rightRotate();
                    cur_node = left_node;
                }
            }
            cur_node.fixupHeight();
            cur_node = cur_node.parent;
        }
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public Iterator<T> preIterator() {
        return new AVLTreePreIterator(size());
    }

    @Override
    public Iterator<T> postIterator() {
        return new AVLTreePostIterator(size());
    }

    /**
     * 删除元素总体的思路就是把删除元素的后继节点移动到被删除的节点上，然后调整树结构
     * 删除元素的时候有三种情况，
     * 1，右子树不存在，那么直接把左子树移动到父节点位置
     * 2，左子树不存在，那么直接把右子树移动到父节点位置
     * 3，左右子树都存在，那么取出后继节点，后继节点肯定满足上面的1，2种条件。
     *
     * @param ele
     */
    @Override
    public void delete(T ele) {
        Preconditions.checkNotNull(ele);
        Node delete_node = findNode(ele);
        if (delete_node != null) {
            _size--;
            if (delete_node.getLeft() == null) {
                transplantNode(delete_node, delete_node.getRight());
                deleteFixup(delete_node, delete_node.getRight());
            } else if (delete_node.getRight() == null) {
                transplantNode(delete_node, delete_node.getLeft());
                deleteFixup(delete_node, delete_node.getLeft());
            } else {
                Node successor = _successor(delete_node);
                Node successor_right = successor.getRight();
                transplantNode(successor, successor_right);
                transplantNode(delete_node, successor);
                deleteFixup(successor, successor_right);
            }
        }
    }

    private void deleteFixup(Node delete_node, Node new_node) {
        if (new_node == null) {
            fixUp(delete_node.getParent());
        } else {
            fixUp(new_node);
        }
    }

    private void transplantNode(Node origin, Node new_node) {
        if (origin.parent == null) {
            if (new_node == null) {
                _head.setValue(null);
                _head.setHeight(0);
            } else {
                _head = new_node;
                _head.setParent(null);
            }
            return;
        }
        if (origin.parent.getLeft() == origin) {
            origin.parent.setLeft(new_node);
        } else {
            origin.parent.setRight(new_node);
        }
        if (new_node != null) {
            new_node.setParent(origin.parent);
        }
    }

    public T successor(T ele) {
        Preconditions.checkNotNull(ele);
        Node node = findNode(ele);
        if (node != null) {
            node = _successor(node);
            if (node != null) {
                return node.getValue();
            }
        }
        return null;
    }

    public T predecessor(T ele) {
        Preconditions.checkNotNull(ele);
        Node node = findNode(ele);
        if (node != null) {
            node = _predecessor(node);
            if (node != null) {
                return node.getValue();
            }
        }
        return null;
    }

    private Node _successor(Node start_node) {
        Node cur_node = start_node.getRight();
        while (cur_node != null) {
            if (cur_node.getLeft() == null) {
                return cur_node;
            }
            cur_node = cur_node.getLeft();
        }
        cur_node = start_node;
        while (cur_node != null) {
            if (cur_node.getParent() == null) {
                return null;
            } else if (cur_node == cur_node.getParent().getLeft()) {
                return cur_node.getParent();
            }
            cur_node = cur_node.getParent();
        }
        return null;
    }

    private Node _predecessor(Node start_node) {
        Node cur_node = start_node.getLeft();
        while (cur_node != null) {
            if (cur_node.getRight() == null) {
                return cur_node;
            }
            cur_node = cur_node.getRight();
        }
        cur_node = start_node;
        while (cur_node != null) {
            if (cur_node.getParent() == null) {
                return null;
            } else if (cur_node == cur_node.getParent().getRight()) {
                return cur_node.getParent();
            }
            cur_node = cur_node.getParent();
        }
        return null;
    }

    @Override
    public T max() {
        Node cur_node = _head;
        while (cur_node.getRight() != null) {
            cur_node = cur_node.getRight();
        }
        return cur_node.getValue();
    }

    @Override
    public T min() {
        Node cur_node = _head;
        while (cur_node.getLeft() != null) {
            cur_node = cur_node.getLeft();
        }
        return cur_node.getValue();
    }

    @Override
    public T kthElement(int k) {
        Preconditions.checkPositionIndex(k, size());
        int cur_pos = 0;
        for (T ele : this) {
            if (cur_pos == k) {
                return ele;
            }
            cur_pos++;
        }
        return null;
    }

    @Override
    public boolean contains(T ele) {
        return findNode(ele) != null;
    }

    @Override
    public boolean isBalance() {
        return checkHeight(_head) != -1;
    }

    private int checkHeight(Node root) {
        if (root == null) {
            return 0;
        }

        int leftHeight = checkHeight(root.getLeft());
        if (leftHeight == -1) {
            return -1;
        }

        int rightHeight = checkHeight(root.getRight());
        if (rightHeight == -1) {
            return -1;
        }

        if (Math.abs(rightHeight - leftHeight) > 1) {
            return -1;
        } else {
            return Math.max(rightHeight, leftHeight) + 1;
        }
    }

    T findOrigin(T ele) {
        Node result = findNode(ele);
        return result == null ? null : result.getValue();
    }

    private Node findNode(T ele) {
        Preconditions.checkNotNull(ele);
        if (_head.getValue() == null) {
            return null;
        }
        Node cur_node = _head;
        while (cur_node != null) {
            cur_node.incHeight();
            int result = cur_node.getValue().compareTo(ele);
            if (result == 0 && cur_node.getValue().equals(ele)) {
                return cur_node;
            } else if (result < 0) {
                cur_node = cur_node.getRight();
            } else {
                cur_node = cur_node.getLeft();
            }
        }
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return new AVLTreeMiddleIterator(size());
    }

    private int leftHeight(Node cur_node) {
        return cur_node.getLeft() == null ? -1 : cur_node.getLeft().getHeight();
    }

    private int rightHeight(Node cur_node) {
        return cur_node.getRight() == null ? -1 : cur_node.getRight().getHeight();
    }

    @Override
    public String toString() {
        return _head.toString();
    }

    private void checkCurrencyModify(int expect_size) {
        if (size() != expect_size) {
            throw new ConcurrentModificationException();
        }
    }

    private class Node implements Comparable<Node> {
        private T value;
        private Node left;
        private Node right;
        private Node parent;
        private int height;

        public Node(Node left, Node parent, Node right, T value) {
            this.left = left;
            this.parent = parent;
            this.right = right;
            this.value = value;
            this.height = 0;

        }

        public Node(Node left, Node parent, Node right) {
            this(left, parent, right, null);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("\tNode{");
            sb.append("height=").append(height);
            sb.append(", value=").append(value);
            if (left != null) {
                sb.append(",\n\t left=").append(left);
            }
            if (right != null) {
                sb.append(",\n\t right=").append(right);
            }
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (height != node.height) return false;
            if (!value.equals(node.value)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = value.hashCode();
            result = 31 * result + height;
            return result;
        }

        public T getValue() {

            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int incHeight() {
            return ++height;
        }

        public int decHeight() {
            return --height;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public void leftRotate() {
            Node grand_node = parent.getParent();
            if (grand_node != null) {
                if (grand_node.getLeft() == parent) {
                    grand_node.setLeft(this);
                } else {
                    grand_node.setRight(this);
                }
            } else {
                _head = this;
            }
            parent.setRight(getLeft());
            if (getLeft() != null) {
                getLeft().setParent(parent);
            }
            setLeft(parent);
            parent.setParent(this);
            parent.fixupHeight();
            setParent(grand_node);
            fixupHeight();
        }

        public void rightRotate() {
            Node grand_node = parent.getParent();
            if (grand_node != null) {
                if (grand_node.getLeft() == parent) {
                    grand_node.setLeft(this);
                } else {
                    grand_node.setRight(this);
                }
            } else {
                _head = this;
            }
            parent.setLeft(getRight());
            if (getRight() != null) {
                getRight().setParent(parent);
            }
            setRight(parent);
            parent.setParent(this);
            parent.fixupHeight();
            setParent(grand_node);
            fixupHeight();
        }

        public void fixupHeight() {
            int left_height = (left == null) ? -1 : left.getHeight();
            int right_height = (right == null) ? -1 : right.getHeight();
            height = Math.max(left_height, right_height) + 1;
        }

        @Override
        public int compareTo(Node o) {
            return value.compareTo(o.value);
        }
    }

    private class AVLTreePreIterator implements Iterator<T> {

        int iter_size;
        LinkedStack<Node> _stacks;
        Node cur_node;

        public AVLTreePreIterator(int size) {
            iter_size = size;
            _stacks = new LinkedStack<>();
            if (_head.getValue() != null) {
                _stacks.push(_head);
            }
            cur_node = null;
        }

        @Override
        public boolean hasNext() {
            checkCurrencyModify(iter_size);
            boolean result = !_stacks.isEmpty();
            cur_node = _stacks.pop();
            if (cur_node != null) {
                if (cur_node.getLeft() != null) {
                    _stacks.push(cur_node.getLeft());
                }
                if (cur_node.getRight() != null) {
                    _stacks.push(cur_node.getRight());
                }
            }
            return result;
        }

        @Override
        public T next() {
            checkCurrencyModify(iter_size);
            return cur_node.getValue();
        }
    }

    private class AVLTreeMiddleIterator implements Iterator<T> {
        private int iter_size;
        private LinkedStack<Node> _stacks;
        private Node cur_node;

        public AVLTreeMiddleIterator(int _size) {
            iter_size = _size;
            _stacks = new LinkedStack<>();
            if (_head.getValue() != null) {
                cur_node = _head;
                insertLeft(cur_node);
            }
        }

        private void insertLeft(Node start_node) {
            Node cur_start_node = start_node;
            while (cur_start_node != null) {
                _stacks.push(cur_start_node);
                cur_start_node = cur_start_node.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            checkCurrencyModify(iter_size);
            if (!_stacks.isEmpty()) {
                cur_node = _stacks.pop();
                insertLeft(cur_node.getRight());
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            checkCurrencyModify(iter_size);
            return cur_node.getValue();
        }
    }

    private class AVLTreePostIterator implements Iterator<T> {
        private int iter_size;
        private LinkedStack<Node> _stacks;
        private Node cur_node;

        public AVLTreePostIterator(int _size) {
            iter_size = _size;
            _stacks = new LinkedStack<>();
            if (_head.getValue() != null) {
                cur_node = _head;
                insertRight(cur_node);
            }
        }

        private void insertRight(Node start_node) {
            checkCurrencyModify(iter_size);
            Node cur_start_node = start_node;
            while (cur_start_node != null) {
                _stacks.push(cur_start_node);
                cur_start_node = cur_start_node.getRight();
            }
        }

        @Override
        public boolean hasNext() {
            checkCurrencyModify(iter_size);
            if (!_stacks.isEmpty()) {
                cur_node = _stacks.pop();
                insertRight(cur_node.getLeft());
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            checkCurrencyModify(iter_size);
            return cur_node.getValue();
        }
    }
}
