package ssj.algorithm.collections;

import com.google.common.base.Preconditions;
import ssj.algorithm.SearchTree;
import ssj.algorithm.lang.Tuple2;

import java.util.Iterator;

/**
 * 在B树中支持重复元素比较复杂，因此暂时不要把重复元素插入进去
 * Created by shenshijun on 15/2/5.
 */
public class BTree<T extends Comparable<? super T>> implements SearchTree<T> {
    private static final int DEFAULT_DEGREE = 4;
    private int tree_degree;
    private BTreeNode<T> root;
    private int tree_size;

    private BTree(int degree) {
        Preconditions.checkArgument(degree >= 2);
        tree_degree = degree;
        tree_size = 0;
        root = new BTreeNode<>(tree_degree);
    }

    public static <T extends Comparable<? super T>> BTree<T> degreeOf(int degree) {
        return new BTree<>(degree);
    }

    public static <T extends Comparable<? super T>> BTree<T> defaultDegree() {
        return new BTree<>(DEFAULT_DEGREE);
    }

    @Override
    public void add(T ele) {
        Preconditions.checkNotNull(ele);

        BTreeNode<T> leaf_node = root;
        for (BTreeNode<T> cur_node = root; cur_node != null && !cur_node.isValueEmpty(); ) {
            leaf_node = cur_node;
            for (int i = 1; i < cur_node.size(); i += 2) {
                int cmp_res = cur_node.getValue(i).compareTo(ele);
                //如果非空，那么是最后一个子节点
                if (cmp_res < 0 && i == cur_node.size() - 2) {
                    cur_node = cur_node.getChild(i + 1);
                    break;
                } else if (cmp_res >= 0) {
                    cur_node = cur_node.getChild(i - 1);
                    break;
                }
            }

            if (leaf_node.isValueFull() && cur_node != null) {
                splitNode(leaf_node, null);
            }
        }

        if (leaf_node.isValueFull()) {
            splitNode(leaf_node, ele);
        } else {
            leaf_node.appendValue(ele);
        }
        tree_size++;
    }

    private Tuple2<BTreeNode<T>, BTreeNode<T>> splitNode(BTreeNode<T> node, T insert_ele) {
        Preconditions.checkNotNull(node);

        BTreeNode<T> right_node = new BTreeNode<>(tree_degree);
        BTreeNode<T> parent_node = node.getParent();
        if (node == root) {
            parent_node = new BTreeNode<>(tree_degree);
            root = parent_node;
        }
        right_node.setParent(parent_node);

        //中间节点插入父节点，子节点的中点在degree－1。因为从0开始计数，所以需要减一
        int mid_index = parent_node.appendValue(node.deleteValue(node.size() / 2));
        parent_node.setChild(mid_index - 1, node);
        parent_node.setChild(mid_index + 1, right_node);

        //把一半元素移动到新到节点中
        for (int i = node.size() / 2; i < node.size(); i += 1) {
            if (i % 2 == 0) {
                right_node.setChild(i - node.size() / 2, node.deleteChild(i));
            } else {
                right_node.setValue(i - node.size() / 2, node.deleteValue(i));
            }
        }

        if (insert_ele != null) {
            if (parent_node.getValue(mid_index).compareTo(insert_ele) > 0) {
                right_node.appendValue(insert_ele);
            } else {
                node.appendValue(insert_ele);
            }
        }

        return new Tuple2<>(node, right_node);
    }

    @Override
    public int size() {
        return tree_size;
    }

    @Override
    public Iterator<T> preIterator() {
        return null;
    }

    @Override
    public Iterator<T> postIterator() {
        return null;
    }

    @Override
    public void delete(T ele) {

    }

    @Override
    public T successor(T value) {
        return null;
    }

    @Override
    public T predecessor(T value) {
        return null;
    }

    @Override
    public T max() {
        return null;
    }

    @Override
    public T min() {
        return null;
    }

    @Override
    public boolean contains(T ele) {
        return false;
    }

    @Override
    public boolean isBalance() {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    private static class BTreeNode<T extends Comparable<? super T>> {
        private final int DEGREE;
        private final int HALF_CAPACITY;
        private final int CAPACITY;
        //奇数位置上存储子节点，偶数位置上存储关键字。
        private Object[] values;
        private BTreeNode<T> parent;
        private int value_index = -1;

        public BTreeNode(int degree) {
            this.DEGREE = degree;
            HALF_CAPACITY = 2 * DEGREE - 1;
            CAPACITY = 4 * DEGREE - 1;
        }

        public BTreeNode<T> getParent() {
            return parent;
        }

        public void setParent(BTreeNode<T> parent) {
            this.parent = parent;
        }

        private void extend(int expect) {
            if (expect >= HALF_CAPACITY) {
                Object[] new_values = new Object[CAPACITY];
                if (values != null) {
                    System.arraycopy(values, 0, new_values, 0, values.length);
                }
                values = new_values;
            } else if (values == null) {
                values = new Object[HALF_CAPACITY];
            }

        }

        @SuppressWarnings("unchecked")
        public BTreeNode<T> getChild(int index) {
            Preconditions.checkPositionIndex(index, CAPACITY);
            Preconditions.checkArgument(index % 2 == 0, "wrong index");

            extend(index);
            return (BTreeNode<T>) values[index];
        }

        @SuppressWarnings("unchecked")
        public T getValue(int index) {
            Preconditions.checkPositionIndex(index, CAPACITY);
            Preconditions.checkArgument(index % 2 == 1, "wrong index");

            extend(index);
            return (T) values[index];
        }

        public int size() {
            extend(1);
            return values.length;
        }

        public boolean isValueFull() {
            return size() == CAPACITY && value_index == CAPACITY - 2;
        }

        public boolean isValueEmpty() {
            return value_index < 0;
        }

        public boolean isChildFull() {
            boolean result = true;
            for (int i = 0; i < size(); i += 2) {
                if (values[i] == null) {
                    result = false;
                }
            }
            return result;
        }

        public boolean isChildEmpty() {
            boolean result = true;
            for (int i = 1; i < size(); i += 2) {
                if (values[i] != null) {
                    result = false;
                }
            }
            return result;
        }

        public boolean addChild(int index, BTreeNode<T> child) {
            Preconditions.checkPositionIndex(index, CAPACITY);
            Preconditions.checkArgument(index % 2 == 0, "wrong index");
            extend(index);
            if (isChildFull()) return false;
            for (int i = size() - 1; i >= index; i -= 2) {
                if (values[i] != null) {
//                    values[i] =
                }
            }

            values[index] = child;
            return false;
        }

        /**
         * 按照排序的顺序把元素插入数组中
         *
         * @param value
         * @return 插入的位置，这样好确定子节点的位置
         */
        public int appendValue(T value) {
            if (isValueFull()) return -1;
            extend(value_index + 2);

            int last_empty = -1;
            //从头开始首先压缩节点，然后再插入
            for (int i = 1; i < size(); i += 2) {
                if (getValue(i) == null && last_empty == -1) {
                    last_empty = i;
                } else if (last_empty != -1 && getValue(i) != null) {
                    values[last_empty] = values[i];
                    values[i] = null;
                    while (getValue(last_empty) != null) {
                        last_empty++;
                    }
                }
            }
            value_index = last_empty;
            //节点为空
            if (last_empty == 1) {
                values[last_empty] = value;
                value_index = last_empty;
                return last_empty;
            }

            int last_pos = -1;
            for (int i = last_empty - 2; i > 0; i -= 2) {
                if (getValue(i).compareTo(value) >= 0) {
                    last_pos = i;
                    values[i + 2] = values[i];
                } else {
                    values[i + 2] = value;
                    return i + 2;
                }
            }

            if (last_pos > 0) {
                values[last_pos] = value;
            }
            return -1;

        }


        public void setValue(int index, T ele) {
            Preconditions.checkPositionIndex(index, CAPACITY);
            Preconditions.checkArgument(index % 2 == 1, "wrong index");
            extend(index);

            values[index] = ele;
        }

        public void setChild(int index, BTreeNode<T> child) {
            Preconditions.checkPositionIndex(index, CAPACITY);
            Preconditions.checkArgument(index % 2 == 0, "wrong index");
            extend(index);

            values[index] = child;
        }

        public T deleteValue(int index) {
            T result = getValue(index);
            setValue(index, null);
            return result;
        }

        public BTreeNode<T> deleteChild(int index) {
            BTreeNode<T> result = getChild(index);
            setChild(index, null);
            return result;
        }

    }
}
