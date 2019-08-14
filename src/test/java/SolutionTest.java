import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class SolutionTest {
    private TreeNode input;
    private List<Integer> expected;
    private Solution soln = new Solution1();

    public SolutionTest(TreeNode input, List<Integer> output) {
        this.input = input;
        this.expected = output;
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][]{
                {createTree("1,#,2,3"), Arrays.asList(1,3,2)},
                {createTree("1"), Arrays.asList(1)}
        });
    }

    private static TreeNode createTree(String s) {
        String[] nodes = s.split(",");
        int layer = 1;
        while (Math.pow(2, layer)-1 <= nodes.length) {
            layer++;
        }
        layer--;

        TreeNode[] treeNodes = new TreeNode[(int)Math.pow(2, layer)-1];
        for (int i = 0; i < Math.pow(2, layer)-1; i++) {
            if (!nodes[i].equals("#")) {
                treeNodes[i] = new TreeNode(Integer.valueOf(nodes[i]));
            } else {
                treeNodes[i] = null;
            }
            if (i%2 == 1) {
                treeNodes[i/2].left = treeNodes[i];
            } else {
                if (i > 0) {
                    treeNodes[i / 2 - 1].right = treeNodes[i];
                }
            }
        }

        int i = (int)Math.pow(2, layer-1)-1;
        while (treeNodes[i] == null) {
            i++;
        }

        int j = (int)Math.pow(2, layer)-1;
        while (j < nodes.length) {
            if (nodes[j].equals("#")) {
                treeNodes[i].left = null;
            } else {
                treeNodes[i].left = new TreeNode(Integer.valueOf(nodes[j]));
            }

            if (j+1 < nodes.length) {
                if (nodes[j + 1].equals("#")) {
                    treeNodes[j].right = null;
                } else {
                    treeNodes[j].right = new TreeNode(Integer.valueOf(nodes[j + 1]));
                }
            }
            j += 2;
            i++;
        }
        return treeNodes[0];
    }

    private void inorderPrint(TreeNode node) {
        if (node != null) {
            inorderPrint(node.left);
            System.out.print(node.val + " ");
            inorderPrint(node.right);
        }
    }

    @Test
    public void testInorderTraversal() {
        System.out.println("expected = ");
        inorderPrint(input);
        System.out.println();
        assertEquals(expected, soln.inorderTraversal(input));
    }
}