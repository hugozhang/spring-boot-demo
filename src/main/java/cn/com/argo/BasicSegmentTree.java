package cn.com.argo;

/**
 * Segment Tree
 * Basic version, support point update (PU) and region query (RU).
 * Underlying is an array with 4*n size, where n is the length of input array.
 */
public class BasicSegmentTree {
    private static final int BASE = 1;

    private final int[] tree;

    private final int[] lazy;

    /**
     * The length of input array.
     */
    private final int size;

    /**
     * Instantiate the segment tree.
     * @param nums the input array.
     */
    public BasicSegmentTree(int[] nums) {
        size = nums.length;

        tree = new int[4 * size];

        lazy = new int[4 * size];

        build(nums, 0, size - 1, BASE);
    }

    /**
     * Replace item at k with val.
     * @param k the index to replace in input array.
     * @param val target value to replace with.
     */
    public void update(int k, int val) {
        doUpdate(k, val, 0, size - 1, BASE);
    }

    /**
     * Get the region sum between [left, right] inclusive.
     * @param left the left index of the region in input array, inclusive.
     * @param right the right index of the region in input array, inclusive.
     * @return the region sum.
     */
    public int query(int left, int right) {
        return doQuery(left, right, 0, size - 1, BASE);
    }

    private void build(int[] nums, int start, int end, int index) {
        if (start == end) {
            tree[index] = nums[start];
            return;
        }
        int mid = start + ((end - start) >> 1);
        build(nums, start, mid, 2 * index);
        build(nums, mid + 1, end, 2 * index + 1);
        pushUp(index);
    }

    private void pushUp(int index) {
        tree[index] = tree[2 * index] + tree[2 * index + 1];
    }

    private void doUpdate(int k, int val, int start, int end, int index) {
        if (start == end) {
            tree[index] = val;
            return;
        }
        int mid = start + ((end - start) >> 1);
        if (k <= mid) {
            doUpdate(k, val, start, mid, 2 * index);
        } else {
            doUpdate(k, val, mid + 1, end, 2 * index + 1);
        }
        pushUp(index);
    }

    /**
     * Get the sum of target region [left, right] starting from current region [start, end].
     * @param left target region left index, inclusive.
     * @param right target region right index, inclusive.
     * @param start current region left index, inclusive.
     * @param end current region right index, inclusive.
     * @param index current index of the segment tree.
     * @return the sum of target region.
     */
    private int doQuery(int left, int right, int start, int end, int index) {
        if (left <= start && end <= right) {
            return tree[index];
        }
        int sum = 0;
        int mid = start + ((end - start) >> 1);
        if (lazy[index] != 0) {
            pushDown(start, mid, end, index);
        }
        /*
         * Case #1: only need to query left half of current region.
         *         [left, right]
         *                       mid
         * Case #2: only need to query right half
         *                            [left, right]
         *                       mid
         * Case #3: query both left and right half
         *                [left,               right]
         *         [start         mid, mid+1           end]
         * Limitation: left <= right, start <= end
         * So, if left > mid, must have mid < left <= right, which is Case #2;
         * if right <= mid, must have left <= right <= mid, which is Case #1;
         * if left <= mid < right, which is Case #3.
         */
        if (left <= mid) {
            sum += doQuery(left, right, start, mid, 2 * index);
        }
        if (right > mid) {
            sum += doQuery(left, right, mid + 1, end, 2 * index + 1);
        }
        return sum;
    }

    /**
     * Add x to all items between [left, right] inclusive.
     * @param left left index of the region.
     * @param right right index of the region.
     * @param x value to add.
     */
    public void add(int left, int right, int x) {
        if (x == 0) {
            return;
        }
        doAdd(left, right, x, 0, size - 1, BASE);
    }

    private void doAdd(int left, int right, int x, int start, int end, int index) {
        if (left <= start && end <= right) {
            tree[index] += (end - start + 1) * x;
            if (start != end) {
                lazy[index] += x;
            }
            return;
        }

        int mid = start + ((end - start) >> 1);

        if (lazy[index] != 0) {
            pushDown(start, mid, end, index);
        }

        if (left <= mid) {
            doAdd(left, right, x, start, mid, 2 * index);
        }
        if (right > mid) {
            doAdd(left, right, x, mid + 1, end, 2 * index + 1);
        }
        pushUp(index);
    }

    private void pushDown(int start, int mid, int end, int index) {
        tree[2 * index] += (mid - start + 1) * lazy[index];
        lazy[2 * index] += lazy[index];

        tree[2 * index + 1] += (end - mid) * lazy[index];
        lazy[2 * index + 1] += lazy[index];

        lazy[index] = 0;
    }

    public static void main(String[] args) {
        int[] a = {10, 11, 12, 13, 14};

        BasicSegmentTree bst = new BasicSegmentTree(a);

//        System.out.println("60 = " + bst.query(0, 4));
//        System.out.println("33 = " + bst.query(0, 2));
//        System.out.println("36 = " + bst.query(1, 3));
//        System.out.println("27 = " + bst.query(3, 4));
//
//        bst.update(2, 15);
//        System.out.println("63 = " + bst.query(0, 4));
//        System.out.println("36 = " + bst.query(0, 2));
//        System.out.println("39 = " + bst.query(1, 3));
//        System.out.println("27 = " + bst.query(3, 4));
        System.out.println("36 = " + bst.query(1, 3));
        bst.add(0,3,10);
        System.out.println("36 = " + bst.query(2, 3));

    }
}
