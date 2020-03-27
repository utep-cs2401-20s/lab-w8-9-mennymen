import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class AminoAcidLLTester {



    @Test
    public void AminoAcidTester1(){
        AminoAcidLL AALL = new AminoAcidLL("GCU");
        AALL.next = new AminoAcidLL("ACG");
        System.out.println(AALL.aminoAcid + " " + AALL.next.aminoAcid + " " );

    }

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

    @Test
    public void aminoAcidListTester1(){
        AminoAcidLL node1 = new AminoAcidLL("GCU");
        AminoAcidLL node2 = new AminoAcidLL("ACG");
        AminoAcidLL node3 = new AminoAcidLL("GAG");
        node1.next = node2;
        node2.next = node3;

        char[] bla = node1.aminoAcidList();
        for(int i = 0; i < bla.length; i++){
            System.out.println(bla[i]);
        }



    }




    @Test
    public void isSortedTester1(){
        AminoAcidLL node1 = new AminoAcidLL("GCU");
        AminoAcidLL node2 = new AminoAcidLL("ACG");
        AminoAcidLL node3 = new AminoAcidLL("GAG");
        node1.next = node2;
        node2.next = node3;
        System.out.println(node1.isSorted());

    }


    @Test
    public void createFromRNASequenceTester1(){
        AminoAcidLL newList = AminoAcidLL.createFromRNASequence("GCUACGGAGCUUCGGAGCUAG");
        while(newList != null) {
            System.out.println(newList.aminoAcid);
            newList = newList.next;
        }
    }


    @Test
    public void sortTester1(){
        AminoAcidLL node1 = new AminoAcidLL("GCU");
        AminoAcidLL node2 = new AminoAcidLL("ACG");
        AminoAcidLL node3 = new AminoAcidLL("GAG");
        node1.next = node2;
        node2.next = node3;
        System.out.println(node1.aminoAcid + " " + node1.next.aminoAcid + " " + node1.next.next.aminoAcid);
        AminoAcidLL.sort(node1);
        System.out.println(node1.aminoAcid + " " + node1.next.aminoAcid + " " + node1.next.next.aminoAcid);

    }

}
