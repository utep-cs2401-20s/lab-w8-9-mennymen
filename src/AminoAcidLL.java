class AminoAcidLL{
  char aminoAcid;
  String[] codons;
  int[] counts;
  AminoAcidLL next;

  AminoAcidLL(){
  }

  /********************************************************************************************/
  /* Creates a new node, with a given amino acid/codon 
   * pair and increments the codon counter for that codon.
   * NOTE: Does not check for repeats!! */
  AminoAcidLL(String inCodon){
    this.aminoAcid = AminoAcidResources.getAminoAcidFromCodon(inCodon);
    this.codons = AminoAcidResources.getCodonListForAminoAcid(aminoAcid);
    this.counts = new int[codons.length];
    for (int i = 0; i < codons.length; i++) {
      if(codons[i].equals(inCodon))
        counts[i]++;
    }
    this.next = null;
  }

  /********************************************************************************************/
  /* Recursive method that increments the count for a specific codon:
   * If it should be at this node, increments it and stops, 
   * if not passes the task to the next node. 
   * If there is no next node, add a new node to the list that would contain the codon. 
   */
  private void addCodon(String inCodon){
    if(this.aminoAcid == AminoAcidResources.getAminoAcidFromCodon(inCodon)) {
      for (int i = 0; i < this.codons.length; i++) {
        if (inCodon.equals(this.codons[i])) {
          this.counts[i]++;
          return;
        }
      }
    }

    if(this.next == null){
      this.next = new AminoAcidLL(inCodon);
      return;
    }

    this.next.addCodon(inCodon);
  }


  /********************************************************************************************/
  /* Shortcut to find the total number of instances of this amino acid */
  private int totalCount(){
    int totalCount = 0;
    for(int i = 0; i < this.counts.length; i++) {
      totalCount += this.counts[i];
    }
    return totalCount;
  }

  /********************************************************************************************/
  /* helper method for finding the list difference on two matching nodes
  *  must be matching, but this is not tracked */
  private int totalDiff(AminoAcidLL inList){
    return Math.abs(totalCount() - inList.totalCount());
  }


  /********************************************************************************************/
  /* helper method for finding the list difference on two matching nodes
  *  must be matching, but this is not tracked */
  private int codonDiff(AminoAcidLL inList){
    int diff = 0;
    for(int i=0; i<codons.length; i++){
      diff += Math.abs(counts[i] - inList.counts[i]);
    }
    return diff;
  }

  /********************************************************************************************/
  /* Recursive method that finds the differences in **Amino Acid** counts. 
   * the list *must* be sorted to use this method */
  public int aminoAcidCompare(AminoAcidLL inList){
    if(this.next == null && inList.next == null){
      return this.totalCount() - inList.totalCount();
    }

    if(this.next == null){
        return inList.totalCount() + this.aminoAcidCompare(inList.next);
    }

    if(inList.next == null){
        return this.totalCount() + this.next.aminoAcidCompare(inList);
    }

    return Math.abs((this.totalCount() - inList.totalCount()) + (this.next.aminoAcidCompare(inList.next)));
  }

  /********************************************************************************************/
  /* Same ad above, but counts the codon usage differences
   * Must be sorted. */
  public int codonCompare(AminoAcidLL inList){
    return 0;
  }


  /********************************************************************************************/
  /* Recursively returns the total list of amino acids in the order that they are in in the linked list. */
  public char[] aminoAcidList(){

    if(this.next == null)
      return new char[] {this.aminoAcid};

    char[] aminos = {this.aminoAcid};

    return mergeArrays(aminos, this.next.aminoAcidList());

    /*
    AminoAcidLL head = this;

    int count = 0;
    if(this.next == null){
      for(AminoAcidLL i = this; i != null; i = head.next){
        count++;
      }
      char[] aminos = new char[count];
      count = 0;
      for(AminoAcidLL j = this; j != null; j = head.next){
        aminos[count] = j.aminoAcid;
        count++;
      }
      return aminos;
    }

    return head.next.aminoAcidList();
    */}

  /********************************************************************************************/
  /* Recursively returns the total counts of amino acids in the order that they are in in the linked list. */
  public int[] aminoAcidCounts(){


    return new int[]{};
  }


  /********************************************************************************************/
  /* recursively determines if a linked list is sorted or not */
  public boolean isSorted(){
    //If there is nothing after the current node, that means, this is the end of the list
    if(this.next == null)
      return true;

    //If the current amino acid is greater than the next one (comes next alphabetically), it is not in sorted.
    if(this.aminoAcid > this.next.aminoAcid)
        return false;

    return this.next.isSorted();
  }


  /********************************************************************************************/
  /* Static method for generating a linked list from an RNA sequence */
  public static AminoAcidLL createFromRNASequence(String inSequence){
    if(inSequence.length() < 3)
      return null;

    AminoAcidLL aminoList = null;
    while(inSequence.length() > 2 && AminoAcidResources.getAminoAcidFromCodon(inSequence.substring(0,3)) != '*'){
      if(aminoList == null) {
        aminoList = new AminoAcidLL(inSequence.substring(0,3));
      }

      else{
        aminoList.addCodon(inSequence.substring(0,3));
      }

      inSequence = inSequence.substring(3);

    }


    return aminoList;
  }


  /********************************************************************************************/
  /* sorts a list by amino acid character*/
  public static AminoAcidLL sort(AminoAcidLL inList){

    //if list is empty or there is only one node in the list, there is nothing to sort
    if(inList == null || inList.next == null)
      return inList;

    AminoAcidLL head = inList;
    AminoAcidLL headPrevious = inList;
    AminoAcidLL temp;
    AminoAcidLL min;
    AminoAcidLL minPrevious;

    while(head.next != null){
      min = head;
      minPrevious = min;
      temp = head.next;
      //looks for the first amino acid in alphabetical order, starting from the head
      while(temp != null){
        if (temp.aminoAcid < min.aminoAcid)
          min = temp;
        temp = temp.next;
      }

      //this if is used in case the head is already the first amino acid in the list, we continue to the next iteration
      if(minPrevious == min) {
        head = head.next;
        continue;
      }

      //find the position of the node before the node that will be moved
      while(minPrevious.next != min){
        minPrevious = minPrevious.next;
      }


        minPrevious.next = min.next;
        min.next = head;

        if (headPrevious != head)
          headPrevious.next = min;

      headPrevious = min;
    }

    //returns list
    return inList;
  }







//**********************************************************************
  private char[] mergeArrays(char[] a, char[] b) {
    char[] c = new char[a.length + b.length];
    for(int i = 0; i < a.length; i++){
      c[i] = a[i];
    }
    int j = 0;
    for (int i = a.length; i < c.length; i++) {
      c[i] = b[j];
      j++;
    }

    return c;
  }
}

