package ssj.algorithm.collections;

import com.google.common.base.Preconditions;
import ssj.algorithm.SearchTree;
import ssj.algorithm.util.CheckUtil;

import java.util.Iterator;

/**
 * Created by shenshijun on 15/2/5.
 */
public class RedBlackTree<T extends Comparable<? super T>> implements SearchTree<T> {
    private Node<T> root = null;
    private int tree_size = 0;

    private RedBlackTree() {
    }

    public static <T extends Comparable<? super T>> RedBlackTree<T> newInstance() {
        return new RedBlackTree<>();
    }

    @Override
    public void add(T ele) {
        Preconditions.checkNotNull(ele);
        Node<T> new_node = new Node<>(TreeNodeColor.RED, ele);
        if (root == null) {
            root = new_node;
            root.setColor(TreeNodeColor.BLACK);
        } else {
            Node<T> parent_node = null;
            for (Node<T> cur_node = root; cur_node != null; ) {
                parent_node = cur_node;
                if (ele.compareTo(cur_node.getValue()) > 0) {
                    cur_node = cur_node.getRight();
                } else {
                    cur_node = cur_node.getLeft();
                }
            }

            if (parent_node.getValue().compareTo(ele) < 0) {
                parent_node.setRight(new_node);
            } else {
                parent_node.setLeft(new_node);
            }
            new_node.setParent(parent_node);
            addFixUp(new_node);
        }
        tree_size++;
    }

    private void addFixUp(Node<T> start_node) {
        //只有要修改的节点是红色，且其父节点也是红色的时候才需要修复，一般而言start_node一定是红色。
        for (Node<T> fixup_node = start_node; fixup_node.getColor() == TreeNodeColor.RED && fixup_node.getParent().getColor() == TreeNodeColor.RED; ) {
            Node<T> parent_node = fixup_node.getParent();
            Node<T> grand_node = parent_node.getParent();

            if (parent_node == grand_node.getLeft()) {
                if (grand_node.rightColor() == TreeNodeColor.RED) { //case 1
                    parent_node.setColor(TreeNodeColor.BLACK);
                    grand_node.setRightColor(TreeNodeColor.BLACK);
                    grand_node.setColor(TreeNodeColor.RED);
                    fixup_node = grand_node;
                } else if (parent_node.getRight() == fixup_node) { //case 2
                    if (parent_node == root) {
                        root = parent_node.getRight();
                    }
                    parent_node.leftRotate();
                    fixup_node = parent_node;
                } else { //case 3
                    if (grand_node == root) {
                        root = grand_node.getLeft();
                    }
                    grand_node.rightRotate();
                    grand_node.setColor(TreeNodeColor.RED);
                    parent_node.setColor(TreeNodeColor.BLACK);
                    fixup_node = parent_node;
                }
            } else {
                if (grand_node.leftColor() == TreeNodeColor.RED) { //case 1
                    parent_node.setColor(TreeNodeColor.BLACK);
                    grand_node.setLeftColor(TreeNodeColor.BLACK);
                    grand_node.setColor(TreeNodeColor.RED);
                    fixup_node = grand_node;
                } else if (parent_node.getLeft() == fixup_node) {
                    if (parent_node == root) {
                        root = parent_node.getLeft();
                    }
                    parent_node.rightRotate();
                    fixup_node = parent_node;
                } else {
                    if (grand_node == root) {
                        root = grand_node.getRight();
                    }
                    grand_node.leftRotate();
                    grand_node.setColor(TreeNodeColor.RED);
                    parent_node.setColor(TreeNodeColor.BLACK);
                    fixup_node = parent_node;
                }
            }
            root.setColor(TreeNodeColor.BLACK);
        }
    }


    @Override
    public int size() {
        return tree_size;
    }


    @Override
    public void delete(T ele) {
        Preconditions.checkNotNull(ele);
        if (isEmpty()) return;
        Node<T> delete_node = findNode(ele);
        if (delete_node == null) return;

        /*如果两个节点都有，那么找到后继，把后继值移动到这个元素的位置上。
         *这样的话，这个位置就没有违反红黑树的特性，但是后继元素就可能违反特性
         */
        if (delete_node.getLeft() != null && delete_node.getRight() != null) {
            Node<T> successor = _successor(delete_node);
            delete_node.setValue(successor.getValue());
            delete_node = successor;
        }

        Node<T> replace_node = null;
        if (delete_node.getRight() != null) {
            replace_node = delete_node.getRight();
        } else if (delete_node.getLeft() != null) {
            replace_node = delete_node.getLeft();
        }

        //处理根节点并且替换删除的节点
        replaceSubTree(delete_node, replace_node);

        //删除的节点是黑色的时候才需要修复
        if (delete_node.getColor() == TreeNodeColor.BLACK) {
            //从替换的节点开始修复红黑树的特性
            deleteFixUp(replace_node);
        }
        tree_size--;
    }

    public void deleteFixUp(Node<T> node) {
        while (node != root && node.getColor() == TreeNodeColor.BLACK) {
            Node<T> parent_node = node.getParent();
            if (node == parent_node.getLeft()) {
                //兄弟节点一定存在，因为如果这个节点是黑色，如果兄弟节点不存在，那么说明在原来的红黑树中黑高不等。
                //左边大于等于2，右边只有1.
                Node<T> brother_node = parent_node.getRight();
                //兄弟节点如果是红色，那么父节点是黑色，说明删除导致左支黑高度减一
                //因此父节点设红，并且左旋转(左旋相当于把右支上的一个黑节点变成公共黑节点)
                //现在子树上的黑高相等了，但是整个子树黑高比别的分支小一
                if (brother_node.getColor() == TreeNodeColor.RED) {
                    brother_node.setColor(TreeNodeColor.BLACK);
                    parent_node.setColor(TreeNodeColor.RED);
                    leftRotate(parent_node);
                } else {
                    if (brother_node.leftColor() == TreeNodeColor.BLACK && brother_node.rightColor() == TreeNodeColor.BLACK) {
                        brother_node.setColor(TreeNodeColor.RED);
                        node = parent_node;
                    } else if (brother_node.rightColor() == TreeNodeColor.BLACK) {
                        brother_node.setColor(TreeNodeColor.RED);
                        brother_node.setLeftColor(TreeNodeColor.BLACK);
                        rightRotate(brother_node);
                    } else if (brother_node.rightColor() == TreeNodeColor.RED) {
                        brother_node.setColor(parent_node.getColor());
                        parent_node.setColor(TreeNodeColor.BLACK);
                        brother_node.setRightColor(TreeNodeColor.RED);
                        leftRotate(parent_node);
                        node = root;
                    }
                }
            } else {
                Node<T> brother_node = parent_node.getLeft();
                if (brother_node.getColor() == TreeNodeColor.RED) {
                    brother_node.setColor(TreeNodeColor.BLACK);
                    parent_node.setColor(TreeNodeColor.RED);
                    rightRotate(parent_node);
                } else {
                    if (brother_node.leftColor() == TreeNodeColor.BLACK && brother_node.rightColor() == TreeNodeColor.BLACK) {
                        brother_node.setColor(TreeNodeColor.RED);
                        node = parent_node;
                    } else if (brother_node.leftColor() == TreeNodeColor.BLACK) {
                        brother_node.setColor(TreeNodeColor.RED);
                        brother_node.setRightColor(TreeNodeColor.BLACK);
                        leftRotate(brother_node);
                    } else if (brother_node.leftColor() == TreeNodeColor.RED) {
                        brother_node.setColor(parent_node.getColor());
                        parent_node.setColor(TreeNodeColor.BLACK);
                        brother_node.setLeftColor(TreeNodeColor.RED);
                        rightRotate(parent_node);
                        node = root;
                    }
                }

            }
        }
        node.setColor(TreeNodeColor.BLACK);
    }

    /**
     * 替换了整个子树，如果需要仅替换节点，则是replaceNode
     *
     * @param old_node
     * @param new_node
     */
    private void replaceSubTree(Node<T> old_node, Node<T> new_node) {
        Preconditions.checkNotNull(old_node);
        if (old_node == root) {
            root = new_node;
        } else if (old_node.getParent().getLeft() == old_node) {
            old_node.getParent().setLeft(new_node);
        } else {
            old_node.getParent().setRight(new_node);
        }

        if (new_node != null) {
            new_node.setParent(old_node.getParent());
        }
    }

    private void replaceNode(Node<T> old_node, Node<T> new_node) {
        Preconditions.checkNotNull(old_node);
        replaceSubTree(old_node, new_node);
        if (new_node != null) {
            new_node.setLeft(old_node.getLeft());
            new_node.setRight(old_node.getRight());
        }
        old_node.setLeftParent(new_node);
        old_node.setRightParent(new_node);
    }

    private void leftRotate(Node<T> node) {
        if (node == root) {
            root = node.getRight();
        }
        node.leftRotate();
    }

    private void rightRotate(Node<T> node) {
        if (node == root) {
            root = node.getLeft();
        }
        node.rightRotate();
    }


    @Override
    public T successor(T ele) {
        Preconditions.checkNotNull(ele);
        Node<T> node = findNode(ele);
        if (node != null) {
            node = _successor(node);
            if (node != null) {
                return node.getValue();
            }
        }
        return null;
    }

    private Node<T> _successor(Node<T> node) {
        Preconditions.checkNotNull(node);

        for (Node<T> succ_node = node.getRight(); succ_node != null; succ_node = succ_node.getLeft()) {
            if (succ_node.getLeft() == null) {
                return succ_node;
            }
        }

        for (Node<T> succ_node = node; succ_node.getParent() != null; succ_node = succ_node.getParent()) {
            if (succ_node.getParent().getLeft() == succ_node) {
                return succ_node.getParent();
            }
        }

        return null;
    }

    private Node<T> findNode(T ele) {
        for (Node<T> cur_node = root; cur_node != null; ) {
            int com_res = ele.compareTo(cur_node.getValue());
            if (com_res == 0) {
                return cur_node;
            } else if (com_res > 0) {
                cur_node = cur_node.getRight();
            } else {
                cur_node = cur_node.getLeft();
            }
        }
        return null;
    }

    @Override
    public T predecessor(T ele) {
        Preconditions.checkNotNull(ele);
        Node<T> node = findNode(ele);
        if (node != null) {
            node = _predecessor(node);
            if (node != null) {
                return node.getValue();
            }
        }
        return null;
    }

    private Node<T> _predecessor(Node<T> node) {
        Preconditions.checkNotNull(node);

        for (Node<T> succ_node = node.getLeft(); succ_node != null; succ_node = succ_node.getRight()) {
            if (succ_node.getRight() == null) {
                return succ_node;
            }
        }

        for (Node<T> succ_node = node; succ_node.getParent() != null; succ_node = succ_node.getParent()) {
            if (succ_node.getParent().getRight() == succ_node) {
                return succ_node.getParent();
            }
        }

        return null;
    }


    @Override
    public T max() {
        Node<T> max_node = maxNode();
        return (max_node == null) ? null : max_node.getValue();
    }

    private Node<T> maxNode() {
        for (Node<T> cur_node = root; cur_node != null; cur_node = cur_node.getRight()) {
            if (cur_node.getRight() == null) {
                return cur_node;
            }
        }
        return null;
    }

    private Node<T> minNode() {
        for (Node<T> cur_node = root; cur_node != null; cur_node = cur_node.getLeft()) {
            if (cur_node.getLeft() == null) {
                return cur_node;
            }
        }
        return null;
    }

    @Override
    public T min() {
        Node<T> min_node = minNode();
        return (min_node == null) ? null : min_node.getValue();
    }

    @Override
    public boolean contains(T ele) {
        return findNode(ele) != null;
    }

    @Override
    public boolean isBalance() {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new MidTreeIterator();
    }

    @Override
    public Iterator<T> preIterator() {
        return new PreTreeIterator();

    }

    @Override
    public Iterator<T> postIterator() {
        return new PostTreeIterator();
    }

    private enum TreeNodeColor {
        RED, BLACK
    }

    private static class Node<T extends Comparable<? super T>> {
        private T value;
        private TreeNodeColor color;
        private Node<T> parent;
        private Node<T> left;
        private Node<T> right;


        public Node(TreeNodeColor color, T value) {
            this.color = color;
            this.value = value;
        }

        public TreeNodeColor getColor() {
            return color;
        }

        public void setColor(TreeNodeColor color) {
            this.color = color;
        }

        public Node<T> getLeft() {
            return left;
        }

        public void setLeft(Node<T> left) {
            this.left = left;
        }

        public Node<T> getParent() {
            return parent;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public Node<T> getRight() {
            return right;
        }

        public void setRight(Node<T> right) {
            this.right = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (color != node.color) return false;
            if (!value.equals(node.value)) return false;
            if (parent != node.parent) return false;
            if (left != node.left) return false;
            if (right != node.right) return false;

            return true;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("color=").append(color);
            sb.append(", value=").append(value);
            sb.append('}');
            return sb.toString();
        }

        public TreeNodeColor leftColor() {
            return (getLeft() != null) ? getLeft().getColor() : TreeNodeColor.BLACK;
        }

        public void setLeftColor(TreeNodeColor color) {
            if (getLeft() != null) {
                getLeft().setColor(color);
            }
        }

        public TreeNodeColor rightColor() {
            return (getRight() != null) ? getRight().getColor() : TreeNodeColor.BLACK;
        }

        public void setRightColor(TreeNodeColor color) {
            if (getRight() != null) {
                getRight().setColor(color);
            }
        }

        public void setRightParent(Node<T> parent) {
            if (getRight() != null) {
                getRight().setParent(parent);
            }
        }

        public void setLeftParent(Node<T> parent) {
            if (getLeft() != null) {
                getLeft().setParent(parent);
            }
        }

        //rotate的时候一定要处理这个节点就是根节点的问题
        public void leftRotate() {
            Node<T> parent_node = getParent();
            Node<T> right_node = getRight();
            if (right_node != null) {
                setParent(right_node);
                setRight(right_node.getLeft());
                right_node.setLeftParent(this);
                right_node.setLeft(this);
                right_node.setParent(parent_node);
                if (parent_node != null) {
                    if (parent_node.getRight() == this) {
                        parent_node.setRight(right_node);
                    } else {
                        parent_node.setLeft(right_node);
                    }
                }
            }
        }

        //rotate的时候一定要处理这个节点就是根节点的问题
        public void rightRotate() {
            Node<T> parent_node = getParent();
            Node<T> left_node = getLeft();
            if (left_node != null) {
                setParent(left_node);
                setLeft(left_node.getRight());
                left_node.setRightParent(this);
                left_node.setRight(this);
                left_node.setParent(parent_node);
                if (parent_node != null) {
                    if (parent_node.getRight() == this) {
                        parent_node.setRight(left_node);
                    } else {
                        parent_node.setLeft(left_node);
                    }
                }
            }
        }

        @Override
        public int hashCode() {
            int result = value.hashCode();
            result = 31 * result + color.hashCode();
            return result;
        }

        public T getValue() {
            return value;

        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    private class PreTreeIterator implements Iterator<T> {

        int iter_size;
        LinkedStack<Node<T>> _stacks;
        Node<T> cur_node;


        public PreTreeIterator() {
            iter_size = size();
            _stacks = new LinkedStack<>();
            if (root != null) {
                _stacks.push(root);
            }
            cur_node = null;
        }

        @Override
        public boolean hasNext() {
            CheckUtil.checkCurrencyModify(iter_size, size());
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
            CheckUtil.checkCurrencyModify(iter_size, size());
            return cur_node.getValue();
        }
    }

    private class MidTreeIterator implements Iterator<T> {
        private int iter_size;
        private LinkedStack<Node<T>> _stacks;
        private Node<T> cur_node;

        public MidTreeIterator() {
            iter_size = size();
            _stacks = new LinkedStack<>();
            if (root != null) {
                cur_node = root;
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
            CheckUtil.checkCurrencyModify(iter_size, size());
            if (!_stacks.isEmpty()) {
                cur_node = _stacks.pop();
                insertLeft(cur_node.getRight());
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            CheckUtil.checkCurrencyModify(iter_size, size());
            return cur_node.getValue();
        }
    }

    private class PostTreeIterator implements Iterator<T> {
        private int iter_size;
        private LinkedStack<Node> unvisited_stack;
        private LinkedStack<Node> visited_stack;
        private Node<T> cur_node;

        public PostTreeIterator() {
            iter_size = size();
            unvisited_stack = new LinkedStack<>();
            visited_stack = new LinkedStack<>();
            if (root != null) {
                unvisited_stack.push(root);
                initStack();
            }
        }

        private void initStack() {
            if (!unvisited_stack.isEmpty()) {
                Node this_node = unvisited_stack.head();
                while (!shouldVisit(this_node)) {
                    if (this_node.getRight() != null) {
                        unvisited_stack.push(this_node.getRight());
                    }

                    if (this_node.getLeft() != null) {
                        unvisited_stack.push(this_node.getLeft());
                    }
                    this_node = unvisited_stack.head();
                }
            }
        }

        private Node insertUnvisitedNode(Node node) {
            Node this_node = node;
            while (!shouldVisit(this_node)) {
                unvisited_stack.push(this_node);
                if (this_node.getRight() != null) {
                    unvisited_stack.push(this_node.getRight());
                }

                if (this_node.getLeft() != null) {
                    unvisited_stack.push(this_node.getLeft());
                }
                this_node = unvisited_stack.pop();
            }
            return this_node;
        }

        private boolean shouldVisit(Node node) {
            boolean left_result = false;
            boolean right_result = false;
            Node visited_right_node;
            if (node.getRight() == null) {
                right_result = true;
            } else if ((visited_right_node = visited_stack.head()) != null && (node.getRight() == visited_right_node)) {
                right_result = true;
                visited_stack.pop();
            }

            Node visited_left_node;
            if (node.getLeft() == null) {
                left_result = true;
            } else if ((visited_left_node = visited_stack.head()) != null && (node.getLeft() == visited_left_node)) {
                left_result = true;
                visited_stack.pop();
            }

            return left_result && right_result;
        }

        @Override
        public boolean hasNext() {
            CheckUtil.checkCurrencyModify(iter_size, size());
            boolean result = !unvisited_stack.isEmpty();
            if (!unvisited_stack.isEmpty()) {
                cur_node = insertUnvisitedNode(unvisited_stack.pop());
                visited_stack.push(cur_node);
            }
            return result;
        }

        @Override
        public T next() {
            CheckUtil.checkCurrencyModify(iter_size, size());
            return cur_node.getValue();
        }
    }

}

