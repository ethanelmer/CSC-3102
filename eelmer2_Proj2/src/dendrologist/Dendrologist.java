package dendrologist;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * A testbed for an augmented implementation of an AVL tree
 * 
 * @author William Duncan, Ethan Elmer
 * @see AVLTreeAPI, AVLTree
 * 
 *      <pre>
 * Date: 10-18-2023
 * Course: csc 3102 
 * Programming Project # 2
 * Instructor: Dr. Duncan
 *      </pre>
 */
public class Dendrologist {
	public static void main(String[] args) throws FileNotFoundException, AVLTreeException {
		String usage = "Dendrologist <order-code> <command-file>\n";
		usage += "  <order-code>:\n";
		usage += "  0 ordered by increasing string length, primary key, and reverse lexicographical order, secondary key\n";
		usage += "  -1 for reverse lexicographical order\n";
		usage += "  1 for lexicographical order\n";
		usage += "  -2 ordered by decreasing string length\n";
		usage += "  2 ordered by increasing string length\n";
		usage += "  -3 ordered by decreasing string length, primary key, and reverse lexicographical order, secondary key\n";
		usage += "  3 ordered by increasing string length, primary key, and lexicographical order, secondary key\n";
		if (args.length != 2) {
			System.out.println(usage);
			throw new IllegalArgumentException("There should be 2 command line arguments.");
		}

		// complete the implementation of this method

		// Get the fileName and sortCase from the configurations
		String fileName = args[1];
		int sortCode = Integer.parseInt(args[0]);
		String sortCase;

		AVLTree<String> tree = new AVLTree<String>();

		// 0 ordered by increasing string length, primary key, and reverse
		// lexicographical order, secondary key
		if (sortCode == 0) {
			Comparator<String> zero = (x, y) -> {
				if (x.length() > y.length()) {
					return 1;
				} else if (x.length() < y.length()) {
					return -1;
				} else {
					return y.compareTo(x);
				}

			};
			sortCase = "0";
			tree = new AVLTree<String>(zero);
		}

		// -1 for reverse lexicographical order
		else if (sortCode == -1) {
			Comparator<String> negOne = (x, y) -> {
				return y.compareTo(x);
			};
			sortCase = "-1";
			tree = new AVLTree<String>(negOne);
		}

		// 1 for lexicographical order
		else if (sortCode == 1) {
			Comparator<String> one = (x, y) -> {
				return x.compareTo(y);
			};
			sortCase = "1";
			tree = new AVLTree<String>(one);
		}

		// -2 ordered by decreasing string length
		else if (sortCode == -2) {
			Comparator<String> negTwo = (x, y) -> {
				if (x.length() < y.length()) {
					return 1;
				} 
				else if (x.length()>y.length()){
					return -1;
				}
				else {
					return 0;
				}

			};
			sortCase = "-2";
			tree = new AVLTree<String>(negTwo);
		}

		// 2 ordered by increasing string length
		else if (sortCode == 2) {
			Comparator<String> two = (x, y) -> {
				if (y.length() < x.length()) {
					return 1;
				}
				else if(y.length()>x.length()) {
					return -1;
				}
				else {
					return 0;
				}
			};
			sortCase = "2";
			tree = new AVLTree<String>(two);
		}

		// -3 ordered by decreasing string length, primary key, and reverse
		// lexicographical order, secondary key
		else if (sortCode == -3) {
			Comparator<String> negThree = (x, y) -> {
				if (x.length() < y.length()) {
					return 1;
				} else if (x.length() > y.length()) {
					return -1;
				} else {
					return y.compareTo(x);
				}

			};
			sortCase = "-3";
			tree = new AVLTree<String>(negThree);
		}

		// 3 ordered by increasing string length, primary key, and lexicographical
		// order, secondary key
		else {
			Comparator<String> three = (x, y) -> {
				if (x.length() > y.length()) {
					return 1;
				} else if (x.length() < y.length()) {
					return -1;
				} else {
					return y.compareTo(x);
				}

			};
			sortCase = "3";
			tree = new AVLTree<String>(three);
		}

		// Setting up the file reader
		FileReader reader = new FileReader(fileName);
		Scanner fileScan = new Scanner(reader);

		// Processes the exact file location so that txtFileName just contains the
		// string representing the .txt file
		int LastSplitIndex = fileName.lastIndexOf("\\") + 1;
		String txtFileName = fileName.substring(LastSplitIndex);

		// Setting up the header
		System.out.printf("A Sample Trace: Dendrologist %s %s%n", sortCase, txtFileName);
		System.out.println("------------------------------------------------------------------------");

		while (fileScan.hasNextLine()) {
			// Initialize the tracker
			int tracker = 0;

			String lineInput = fileScan.nextLine();
			String[] splitter = lineInput.split(" ");

			// Gets the text input to determine what method is being done on the tree
			String input = splitter[tracker];

			// Handles the property output
			if (input.compareTo("props") == 0) {
				System.out.println("Properties:");
				System.out.print("size: " + tree.size() + ", ");
				System.out.print("height: " + tree.height() + ", ");
				System.out.println("diameter: " + tree.diameter());
				System.out.print("fibonnaci?= " + tree.isFibonacci() + ", ");
				System.out.println("complete?= " + tree.isComplete() + ", ");
			}

			// Handles the insert output and method
			else if (input.compareTo("insert") == 0) {
				tracker++;
				String inputInt = splitter[tracker];
				tree.insert(inputInt);
				System.out.println("Inserted: " + inputInt + " ");
			}

			// Handles the geneology output
			else if (input.compareTo("gen") == 0) {
				String inputInt = splitter[tracker + 1];
				System.out.print("Geneology: " + inputInt + " ");

				// Outputs UNDEFINED if the proposed geneology node does not exist
				if (tree.inTree(inputInt) == false) {
					System.out.println("UNDEFINED");
				} else {
					System.out.println();
					ArrayList<String> children = tree.getChildren(inputInt);
					System.out.print("parent: " + tree.getParent(inputInt) + ", ");
					// Handles special case of children being empty
					if (children.isEmpty()) {
						System.out.print("left-child: NONE, ");
						System.out.println("right-child: NONE");
					} else {
						System.out.print("left-child: " + children.get(0) + ", ");
						System.out.println("right-child: " + children.get(1) + ", ");
					}
					System.out.print("#ancestors = " + tree.ancestors(inputInt) + ", ");
					System.out.println("#descendants = " + tree.descendants(inputInt));

				}
			}

			// Handles the output of traversal
			else if (input.compareTo("traverse") == 0) {
				System.out.println("pre-Order Traversal:");
				tree.preorderTraverse(x -> {
					System.out.println(x);
					return null;
				});
				System.out.println("In-Order Traversal:");
				tree.traverse(x -> {
					System.out.println(x);
					return null;
				});
				System.out.println("post-Order Traversal:");
				tree.postorderTraverse(x -> {
					System.out.println(x);
					return null;
				});
			}

			// Handles the output and methodology of deletion
			else if (input.compareTo("delete") == 0) {
				String inputInt = splitter[tracker + 1];
				System.out.println("Deleted: " + inputInt);
				tree.remove(inputInt);
			} else {
				throw new IllegalArgumentException("File Parsing Error");
			}
		}

	}
}