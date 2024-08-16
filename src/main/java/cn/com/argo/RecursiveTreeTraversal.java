package cn.com.argo;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) {
        this.val = val;
    }
}
public class RecursiveTreeTraversal {
    public void traverse(TreeNode node) {
        if (node == null) {
            return;
        }
        // 处理当前节点
        System.out.println(node.val);
        // 递归遍历左子树
        traverse(node.left);
        // 递归遍历右子树
        traverse(node.right);
    }
    public static void main(String[] args) {
        // 创建一个简单的二叉树
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        // 遍历二叉树
        RecursiveTreeTraversal traversal = new RecursiveTreeTraversal();
        traversal.traverse(root);
    }
}
