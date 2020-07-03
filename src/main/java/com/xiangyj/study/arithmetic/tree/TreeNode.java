package com.xiangyj.study.arithmetic.tree;

/**
 * 二叉树节点Node
 * 
 * @author Lydck
 *
 * @param <T>
 */
public class TreeNode<T> {
	/** 树节点存储的数据 */
	T data;
	/** parentNode */
	TreeNode<T> pNode;
	/** 左子节点（左子树） */
	TreeNode<T> lNode;
	/** 右子节点（右子树） */
	TreeNode<T> rNode;
	/** 初始化哈夫曼二叉树用到 */
	boolean flag;
	/** 哈夫曼树的叶子节点的权 */
	int weight;

	/**
	 * 初始化哈夫曼树
	 * 
	 * @param weight
	 *            权，正整数组成的数组
	 * @return 哈夫曼树
	 */
	static <T> TreeNode<T> initHafmTree(int[] weight) {
		int n = weight.length;
		int maxWeight = 0;
		// 初始化一个TreeNode列表,哈夫曼树的节点数为加权叶子节点数n的2n-1
		@SuppressWarnings("unchecked")
		TreeNode<T>[] tree = new TreeNode[2 * n - 1];
		for (int i = 0; i < 2 * n - 1; i++) {
			TreeNode<T> temp = new TreeNode<T>();
			temp.flag = true;
			if (i < n) {
				maxWeight = weight[i] > maxWeight ? weight[i] : maxWeight;
				temp.weight = weight[i];
			} else {
				// 非叶子节点的权为0
				temp.weight = 0;
			}
			tree[i] = temp;
		}
		TreeNode<T> x1 = tree[0], x2 = tree[1];
		// 构造哈弗曼树的n-1个非叶子节点
		for (int i = 0; i < n - 1; i++) {
			int m1 = maxWeight, m2 = maxWeight;
			for (int j = 0; j < n + i; j++) {
				if (tree[j].weight < m1 && tree[j].flag) {
					x1 = tree[j];
					m1 = x1.weight;
				} else if (tree[j].weight < m2 && tree[j].flag) {
					x2 = tree[j];
					m2 = x2.weight;
				}
				
			}
			
			x1.pNode = tree[n + i];
			x2.pNode = tree[n + i];
			x1.flag = false;
			x2.flag = false;
			tree[n + i].weight = x1.weight + x2.weight;
			tree[n + i].lNode = x1;
			tree[n + i].rNode = x2;
		}
		for(int i = 0; i < tree.length; i++) {
			System.out.println(tree[i].weight);
		}
		return tree[n];
	}

	public static void main(String[] args) {
		 TreeNode<Object> hafm = initHafmTree(new int[] {1, 2, 3, 4});
		 System.out.println(hafm.weight);
		/*int[] arr = new int[] { 0, 1, 2, 3, 4, 5 };
		int temp = 0;
		int a = 5, b = 5;
		for (int j = 0; j < arr.length/2; j++) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != -1 && arr[i] < a) {
					a = arr[i];
				} else if (arr[i] != -1 && arr[i] < b) {
					b = arr[i];
				}
				if (a > b) {
					temp = a;
					a = b;
					b = temp;
				}
			}
			arr[a] = -1;
			arr[b] = -1;
			System.out.println(a + ", " + b);
		}*/
	}
}
