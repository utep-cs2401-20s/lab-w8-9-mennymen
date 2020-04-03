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

    //If we reached the end of both lists, return the difference in amino acid counts for the last node
    if(this.next == null && inList.next == null){
      return this.totalCount() - inList.totalCount();
    }

    //If the list we're comparing with inList reached the end, it will only keep counting the amino acids from inLists' next nodes
    if(this.next == null){
        return inList.totalCount() + this.aminoAcidCompare(inList.next);
    }

    //If inList reached the end, it will only keep counting the amino acids from the other list
    if(inList.next == null){
        return this.totalCount() + this.next.aminoAcidCompare(inList);
    }

    return this.totalDiff(inList) + (this.next.aminoAcidCompare(inList.next));
  }

  /********************************************************************************************/
  /* Same ad above, but counts the codon usage differences
   * Must be sorted. */

  public int codonCompare(AminoAcidLL inList){
    //If both lists are empty, return 0
    if(this == null && inList == null)
      return 0;

    //When both lists are at their last node, return their codon difference
    if(this.next == null && inList.next == null)
      return codonDiff(inList);

    //If we're at this list's last node, for now, we'll just count inList's counts
    if(this.next == null)
      return -inList.totalCount() + this.codonCompare(inList.next);


    //If we're at inList's last node, for now, we'll just count this list's counts
    if(inList.next == null)
      return this.totalCount() + this.next.codonCompare(inList);

    //If this list's current amino acid comes before (alphabetically) than inList's
    //count all amino acid instances for this list and move to this list's next node
    if(this.aminoAcid < inList.aminoAcid)
      return this.totalCount() + this.next.codonCompare(inList);

    //If inList's current amino acid comes before (alphabetically) than this list's
    //count all amino acid instances for inList and move to inList's next node
    if(this.aminoAcid > inList.aminoAcid)
      return -inList.totalCount() + this.codonCompare(inList.next);

    //When both lists are on the same codon, return the difference using codonDiff
    return this.codonDiff(inList) + this.next.codonCompare(inList.next);

  }


  /********************************************************************************************/
  /* Recursively returns the total list of amino acids in the order that they are in in the linked list. */
  public char[] aminoAcidList(){

    //Base case: if we are at the last node of the list, return a char array with the amino acid of this node
    if(this.next == null)
      return new char[] {this.aminoAcid};

    //Create an array with this node's amino acid
    char[] aminos = {this.aminoAcid};

    //Recursive call where I use the helper method I created to merge arrays, merging the current array
    //which contains the current node's amino acid, and calls the the method with the next node
    return mergeCharArrays(aminos, this.next.aminoAcidList());
   }

  /********************************************************************************************/
  /* Recursively returns the total counts of amino acids in the order that they are in in the linked list. */
  public int[] aminoAcidCounts(){

    //Base case: if we are at the last node of the list, return an int array with the total count of this node
    if(this.next == null)
      return new int[] {this.totalCount()};

    //Create an array with this node's total amino acid count
    int[] counts = {this.totalCount()};

    //Recursive call where I use the helper method I created to merge arrays, merging the current array
    //which contains the current node's amino acid count, and calls the the method with the next node
    return mergeIntArrays(counts, this.next.aminoAcidCounts());
  }

  /********************************************************************************************/
  /* recursively determines if a linked list is sorted or not */
  public boolean isSorted(){
    //Base case: If there is nothing after the current node, that means, this is the end of the list
    if(this.next == null)
      return true;

    //If the current amino acid is greater than the next one (comes next alphabetically), it is not in sorted and returns false.
    if(this.aminoAcid > this.next.aminoAcid)
        return false;

    //Recursive call: call method with next node
    return this.next.isSorted();
  }


  /********************************************************************************************/
  /* Static method for generating a linked list from an RNA sequence */
  public static AminoAcidLL createFromRNASequence(String inSequence){
    //If the input string's length is less than 3, return null, as it is not possible to read it.
    if(inSequence.length() < 3)
      return null;

    //Crete a new list, and read the string by substrings of 3
    AminoAcidLL aminoList = null;
    while(inSequence.length() > 2 && AminoAcidResources.getAminoAcidFromCodon(inSequence.substring(0,3)) != '*'){
      //Create the first node
      if(aminoList == null) {
        aminoList = new AminoAcidLL(inSequence.substring(0,3));
      }

      //Add codon from the substring
      else{
        aminoList.addCodon(inSequence.substring(0,3));
      }

      //Update string as we already read the first 3 letters of the string
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
      //Looks for the first amino acid in alphabetical order, starting from the head
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
    return inList;
  }



//**********************************************************************
  //Helper methods to merge char and int arrays
  private char[] mergeCharArrays(char[] a, char[] b) {
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

  private int[] mergeIntArrays(int[] a, int[] b) {
    int[] c = new int[a.length + b.length];
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