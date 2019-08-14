import java.util.*;

public class Solution1 implements Solution {
    @Override
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) {
            return Collections.EMPTY_LIST;
        } else {
            TreeNode leftmost = root;
            while (leftmost.left != null) {
                leftmost = leftmost.left;
            }

            List<Integer> result = new ArrayList<>();
            while (leftmost != null) {
                result.add(leftmost.val);
                leftmost = inorderNext(root, leftmost);
            }

            return result;
        }
    }

    private TreeNode inorderNext(TreeNode root, TreeNode curr) {
        if (curr.right != null) {
            TreeNode next = curr.right;
            while (next.left != null) {
                next = next.left;
            }
            return next;
        } else {
            TreeNode parent = findParent(root, curr);
            if (parent == null) {
                TreeNode next = root.right;
                while (next != null && next.left != null) {
                    next = next.left;
                }
                return next;
            } else {
                if (curr == parent.left) {
                    return parent;
                } else {
                    TreeNode gparent = findParent(root, parent);
                    while (gparent != null && parent != gparent.left) {
                        parent = gparent;
                        gparent = findParent(root, parent);
                    }
                    return gparent;
                }
            }
        }
    }

    private TreeNode findParent(TreeNode root, TreeNode curr) {
        if (root == curr) {
            return null;
        } else {
            Queue<TreeNode> qu = new LinkedList<>();
            qu.add(root);
            while (!qu.isEmpty()) {
                TreeNode node = qu.remove();
                if (node != null) {
                    if (node.left == curr || node.right == curr) {
                        return node;
                    } else {
                        qu.add(node.left);
                        qu.add(node.right);
                    }
                }
            }
            return null;
        }
    }
}
