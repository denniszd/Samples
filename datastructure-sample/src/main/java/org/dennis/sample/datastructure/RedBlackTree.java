package org.dennis.sample.datastructure;

import java.util.Comparator;

/**
 * 红黑树
 * 五个性质：
 * (1)每个节点要么是红的，要么是黑的
 * (2)根节点是黑的
 * (3)每个叶节点，即空节点（NIL）是黑的
 * (4)如果一个节点是红的，那么它的两个儿子都是黑的
 * (5)对于每个节点，从该节点到其子孙节点的所有路径上包含相同数目的黑节点
 *
 * @author deng.zhang
 * @since 1.0.0 2016-02-24 17:27
 */
public class RedBlackTree<V> {
    /**
     * 红黑树之红色
     */
    private static final boolean RED = false;
    /**
     * 红黑树只黑色
     */
    private static final boolean BLACK = true;

    /**
     * 根节点
     */
    private Node<V> root = null;
    /**
     * 比较器
     */
    private final Comparator<? super V> comparator;

    public RedBlackTree() {
        comparator = null;
    }

    public RedBlackTree(Comparator<? super V> comparator) {
        this.comparator = comparator;
    }

    /**
     * 插入一个值为value的节点
     *
     * @param value 值
     * @return null
     */
    public V insert(V value) {
        if (value == null) {
            throw new NullPointerException();
        }

        Node<V> t = root;
        //根节点为null，插入的节点设置为根节点
        if (t == null) {
            root = new Node<V>(value, null);
            return null;
        }

        //查找父节点
        int cmp;
        Node<V> parent;
        if (comparator != null) {
            do {
                parent = t;
                cmp = comparator.compare(value, parent.value);
                if (cmp < 0) {
                    t = t.left;
                } else {
                    t = t.right;
                }
            } while (t != null);
        } else {
            Comparable<? super V> valueComparator = (Comparable<? super V>) value;
            do {
                parent = t;
                cmp = valueComparator.compareTo(t.value);
                if (cmp < 0) {
                    t = t.left;
                } else {
                    t = t.right;
                }
            } while (t != null);
        }

        //插入节点
        Node<V> node = new Node<V>(value, parent);
        if (cmp < 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }

        fixAfterInsertion(node);

        return null;
    }

    /**
     * 删除值为value的节点
     * 假设删除的节点为deletedNode,删除的节点的兄弟为brotherNode
     * 红黑树删除节点的四种情况：
     * (1)brotherNode是红色的
     * (2)brotherNode是黑色的，且brotherNode的两个孩子都是黑色的
     * (3)brotherNode是黑色的，且brotherNode的左孩子是红色的，右孩子是黑色的
     * (4)brotherNode是黑色的，且brotherNode的右孩子是红色的。
     *
     * @param value 值
     * @return null
     */
    public V delete(V value) {
        fixAfterInsertion(null);
        return null;
    }

    /**
     * 红黑树插入或删除节点后，可能会违背或者破坏红黑树原有的性质，所以为了使插入或者删除节点后的树依然维持为一个新的红黑树，要做如下两个方面的工作：
     * (1)部分节点重新着色
     * (2)调整部分节点指针的指向，即左旋或者右旋
     * 假设当前节点的父节点为parentNode，叔叔为uncleNode
     * 红黑树插入节点的几种情况：
     * (1)当前节点是根节点
     * 原树是空树，此情况只会违法性质2
     * 对策：直接把当前节点的颜色设置为黑色
     * (2)parentNode是黑色的
     * 该插入操作不会违法性质2和性质4，故红黑树没有被破坏
     * 对策：do nothing
     * (3)parentNode是红色的，且uncleNode是红色的
     * 此时parentNode的父节点一定存在，否则插入前就已经不是红黑树
     * 与此同时，又分为parentNode是祖父节点的左孩子还是右孩子，对于对称性，我们只需要解开一个方向就可以了。
     * 在此，我们只考虑parentNode为祖父节点的左孩子的情况
     * 同时，还可以分为newNode是parentNode的左孩子还是右孩子，但是处理方式是一样的，我们将次归为一类。
     * 对策：将parentNode和uncleNode的颜色设置成黑色，祖父节点的颜色设置成红色，把newNode指向其祖父节点，从新的当前节点重新开始算法
     * (4)parentNode是红色的，uncleNode是黑色的，newNode为parentNode的右孩子
     * 对策：parentNode作为新的当前节点，以新的当前节点为支点左旋
     * (5)parentNode是红色的，uncleNode是黑色的，newNode是parentNode的左孩子
     * 对策：
     *
     * @param node 插入的节点
     */
    private void fixAfterInsertion(Node<V> node) {
        node.color = RED;
        while (node != null && node != root && node.parent.color == RED) {
            //节点的父节点是祖父节点的左孩子
            if (parentOf(node) == leftOf(parentOf(parentOf(node)))) {
                Node<V> uncle = rightOf(parentOf(parentOf(node)));
                if (colorOf(uncle) == RED) {
                    setColor(parentOf(node), BLACK);
                    setColor(uncle, BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    node = parentOf(parentOf(node));
                } else {
                    if (node == rightOf(parentOf(node))) {
                        node = parentOf(node);
                        rotateLeft(node);
                    }
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    rotateRight(parentOf(parentOf(node)));
                }
            } else {//节点的父节点是祖父节点的右孩子
                Node<V> uncle = leftOf(parentOf(parentOf(node)));
                if (colorOf(uncle) == RED) {
                    setColor(parentOf(node), BLACK);
                    setColor(uncle, BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    node = parentOf(parentOf(node));
                } else {
                    if (node == leftOf(parentOf(node))) {
                        node = parentOf(node);
                        rotateRight(node);
                    }
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    rotateLeft(parentOf(parentOf(node)));
                }
            }
            root.color = BLACK;
        }
    }

    /**
     * 获取节点的父节点
     *
     * @param node 节点
     * @param <V>  数据类型
     * @return 如果node为null, 则返回null, 否则返回node的父节点
     */
    private static <V> Node<V> parentOf(Node<V> node) {
        return (node == null ? null : node.parent);
    }

    /**
     * 获取节点的左子节点
     *
     * @param node 节点
     * @param <V>  数据类型
     * @return 如果node为null, 则返回null, 否则返回node的左子节点
     */
    private static <V> Node<V> leftOf(Node<V> node) {
        return (node == null) ? null : node.left;
    }

    /**
     * 获取节点的右子节点
     *
     * @param node 节点
     * @param <V>  数据类型
     * @return 如果node为null, 则返回null, 否则返回node的右子节点
     */
    private static <V> Node<V> rightOf(Node<V> node) {
        return (node == null) ? null : node.right;
    }

    /**
     * 获取节点的颜色
     *
     * @param node 节点
     * @param <V>  数据类型
     * @return 如果node为null, 则返回黑色，否则返回node的实际颜色
     */
    private static <V> boolean colorOf(Node<V> node) {
        return (node == null ? BLACK : node.color);
    }

    /**
     * 设置节点的颜色
     *
     * @param node  节点
     * @param color 指定的颜色
     * @param <V>   数据类型
     */
    private static <V> void setColor(Node<V> node, boolean color) {
        if (node != null)
            node.color = color;
    }

    /**
     * 左旋红黑树
     */
    public void rotateLeft(Node<V> node) {
        if (node != null) {
            Node<V> r = node.right;
            node.right = r.left;
            if (r.left != null) {
                r.left.parent = node;
            }
            r.parent = node.parent;
            if (node.parent == null) {
                root = r;
            } else if (node.parent.left == node) {
                node.parent.left = r;
            } else {
                node.parent.right = r;
            }
            r.left = node;
            node.parent = r;
        }
    }

    /**
     * 右旋红黑树
     */
    public void rotateRight(Node<V> node) {
        if (node != null) {
            Node<V> l = node.left;
            node.left = l.right;
            if (l.right != null) {
                l.right.parent = node;
            }
            l.parent = node.parent;
            if (node.parent == null) {
                root = l;
            } else if (node.parent.right == node) {
                node.parent.right = l;
            } else {
                node.parent.left = l;
            }
            l.right = node;
            node.parent = l;
        }
    }

    /**
     * 节点
     */
    static final class Node<V> {
        V value;
        Node<V> left = null;
        Node<V> right = null;
        Node<V> parent = null;
        boolean color = BLACK;

        public Node(V value, Node<V> parent) {
            this.value = value;
            this.parent = parent;
        }
    }
}
