import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class AminoAcidLLTester {

//**NOTE: These tests are in the order the methods are in the AminoAcidLL.java file, but were not done in this order, that is why in some of them, other methods are used


    /*
    * Test to verify if the constructor works properly when given codons as input
    * Input (codons): GCU (1st node), ACG (2nd node)
    * Expected output: A list with two nodes with the amino acids A and T (respectively)
    * Output: A -> T
    * Test passed, meaning that the method creates nodes the way it is supposed to
    * This test was done to make sure that the constructor method for this class works properly
    */
    @Test
    public void AminoAcidTester1(){
        AminoAcidLL AALL = new AminoAcidLL("GCU");
        AALL.next = new AminoAcidLL("ACG");
        System.out.println(AALL.aminoAcid + " " + AALL.next.aminoAcid + " " );
    }



    /*
    * Test to verify if the aminoAcidCompare method works properly and outputs the difference in amino acids between two lists
    * Input (lists compared): 1st list = A -> T -> E   2nd List = A -> T
    * Expected output: 1 as list 1 has 3 amino acids and the second one has 2, there is a difference of only 1
    * Output: 1
    * Test passed, meaning that the method does compare the number of amino acids the way it is supposed to
    * This test was done to make sure that the method does compare the number of amino acids appropriately when the first list has a larger number of amino acids
    */
    @Test
    public void aminoAcidCompareTester1(){

        AminoAcidLL node1 = new AminoAcidLL("GCU");
        AminoAcidLL node2 = new AminoAcidLL("ACG");
        AminoAcidLL node3 = new AminoAcidLL("GAG");
        node1.next = node2;
        node2.next = node3;

        AminoAcidLL node4 = new AminoAcidLL("GCU");
        node4.next = new AminoAcidLL("ACG");

        System.out.println(node1.aminoAcidCompare(node4));

    }



    /*
     * Test to verify if the aminoAcidCompare method works properly and outputs the difference in amino acids between two lists
     * Input (lists compared): 1st list = A -> T   2nd List = L -> T -> A -> E
     * Expected output: 2 as list 1 has 2 amino acids and the second one has 4, there is a difference of only 1
     * Output: 2
     * Test passed, meaning that the method does compare the number of amino acids the way it is supposed to
     * This test was done to make sure that the method does compare the number of amino acids appropriately when the second list has a larger number of amino acids
     */
    @Test
    public void aminoAcidCompareTester2(){

        AminoAcidLL node1 = new AminoAcidLL("GCU");
        AminoAcidLL node2 = new AminoAcidLL("ACG");
        node1.next = node2;

        AminoAcidLL node4 = new AminoAcidLL("CUU");
        AminoAcidLL node5 = new AminoAcidLL("ACG");
        AminoAcidLL node6 = new AminoAcidLL("GCU");
        AminoAcidLL node7 = new AminoAcidLL("GAG");
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;

        System.out.println(node1.aminoAcidCompare(node4));
    }




    /*
     * Test to verify if the codonCompare method works properly and outputs the difference in codons between two lists
     * Input (lists compared): 1st list contains 10 codons   2nd List contains 6 codons
     * Expected output: 4 as list 1 has 10 codons the second one has 6, there is a difference of 4 codons
     * Output: 4
     * Test passed, meaning that the method does compare the number of codons the way it is supposed to
     * This test was done to make sure that the method does compare the number of codons appropriately
     */
    @Test
    public void codonCompareTester1(){
        AminoAcidLL newList = AminoAcidLL.createFromRNASequence("GCUGCUACGGAGCUUCGGAGCGCUGCUCUUUAG");
        AminoAcidLL newList2 = AminoAcidLL.createFromRNASequence("GCUACGGAGCUUCGGAGCUAG");
        System.out.println(newList.codonCompare(newList2));
    }




    /*
     * Test to verify if the aminoAcidList method works properly and outputs an array with the amino acids from the input list
     * Input (list): A -> T -> E
     * Expected output: {A, T, E} (a char array with length equal to the number of nodes of the input list and with the amino acid of each node)
     * Output: {A, T, E}
     * Test passed, meaning that the method does go through the whole list recursively and and outputs the expected array
     * This test was done to make sure that the method does create an array with the amino acids recursively and works properly
     */
    @Test
    public void aminoAcidListTester1(){
        AminoAcidLL node1 = new AminoAcidLL("GCU");
        AminoAcidLL node2 = new AminoAcidLL("ACG");
        AminoAcidLL node3 = new AminoAcidLL("GAG");
        node1.next = node2;
        node2.next = node3;

        char[] aminos = node1.aminoAcidList();
        for(int i = 0; i < aminos.length; i++){
            System.out.println(aminos[i]);
        }
    }




    /*
     * Test to verify if the aminoAcidCounts method works properly and outputs an array with the number of amino acid instances in each node
     * Input (list, number in parenthesis is the number of instances of this amino acid): A(4) -> T(1) -> E(1) -> L(2) -> R(1) -> S(1)
     * Expected output: {4,1,1,2,1,1}
     * Output: {4,1,1,2,1,1}
     * Test passed, meaning that the method does create an array (recursively) with the number of instances of each amino acid in the list
     * This test was done to make sure that the method does create the array of amino acid instances correctly
     */
    @Test
    public void aminoAcidCountsTester1(){
        AminoAcidLL newList = AminoAcidLL.createFromRNASequence("GCUGCUACGGAGCUUCGGAGCGCUGCUCUUUAG");
        int[] count = newList.aminoAcidCounts();
        for(int i = 0; i < count.length; i++){
            System.out.println(count[i]);
        }
    }




    /*
     * Test to verify if the isSorted method works properly and outputs if the input list is in alphabetical order in respect to their amino acids
     * Input (list): A -> T -> E
     * Expected output: false, since T is before E and they are not in alphabetical order
     * Output: false
     * Test passed, meaning that the method does check if the list is in alphabetical order correctly
     * This test was done to make sure that the method does output false if the list is not sorted alphabetically as intended
     */
    @Test
    public void isSortedTester1(){
        AminoAcidLL node1 = new AminoAcidLL("GCU");
        AminoAcidLL node2 = new AminoAcidLL("ACG");
        AminoAcidLL node3 = new AminoAcidLL("GAG");
        node1.next = node2;
        node2.next = node3;
        System.out.println(node1.isSorted());
    }





    /*
     * Test to verify if the isSorted method works properly and outputs if the input list is in alphabetical order in respect to their amino acids
     * Input (list): A -> E -> L -> R -> S -> T
     * Expected output: true
     * Output: true
     * Test passed, meaning that the method does check if the list is in alphabetical order correctly
     * This test was done to make sure that the method does output true if the list is sorted alphabetically as intended with a longer list
     */
    @Test
    public void isSortedTester2(){
        AminoAcidLL newList = AminoAcidLL.createFromRNASequence("GCUGAGCUUCGGAGCACGUAG");
        System.out.println(newList.isSorted());
    }






    /*
     * Test to verify if the createFromRNASequence method works properly and outputs a list with the respective nodes from the RNA sequence input
     * Input: "GCUACGGAGCUUCGGAGCUAG"
     * Expected output: A -> T -> E -> L -> R -> S
     * Output: A -> T -> E -> L -> R -> S
     * Test passed, meaning that the method does create a list from the input RNA sequence as intended
     * This test was done to make sure that the method does create a list with the respective amino acids
     */
    @Test
    public void createFromRNASequenceTester1(){
        AminoAcidLL newList = AminoAcidLL.createFromRNASequence("GCUACGGAGCUUCGGAGCUAG");
        while(newList != null) {
            System.out.println(newList.aminoAcid);
            newList = newList.next;
        }
    }






    /*
     * Test to verify if the sort method works properly and sorts the input list in alphabetical order by the amino acids
     * Input (list): A -> T -> E -> L -> R -> S
     * Expected output: A -> E -> L -> R -> S -> T
     * Output: A -> E -> L -> R -> S -> T
     * Test passed, meaning that the method does sort a list alphabetically by their amino acids
     * This test was done to make sure that the method sort the input list appropriately
     */
    @Test
    public void sortTester1(){
        AminoAcidLL newList = AminoAcidLL.createFromRNASequence("GCUACGGAGCUUCGGAGCUAG");
        AminoAcidLL head = newList;
        while(head != null) {
            System.out.print(head.aminoAcid + " ");
            head = head.next;
        }
        System.out.println("");
        AminoAcidLL.sort(newList);
        while(newList != null) {
            System.out.print(newList.aminoAcid + " ");
            newList = newList.next;
        }
    }
}
