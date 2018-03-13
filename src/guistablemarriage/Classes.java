package guistablemarriage;
class Person {
    private String name;
    public boolean isEngaged;
    private Person match;
    private llPreferences list;

    Person(String name) {
        this.name   = name;
        list = new llPreferences();
    }
    public String getName() {return name;}
    public String getMatchName() {return this.match.getName();}
    public void voidMatch() {
        this.match = null;
        this.isEngaged = false;
    }
    public void addPreference(Person newPreference) {
        list.addLast(newPreference);
    }
    public void listPreferences() {
        list.listElements();
    }
    public void propose() {
        Person choice;
        boolean accepted;
        choice = list.getFirst();
        accepted = choice.evaluateProposal(this);
        if (accepted) {
            this.isEngaged = true;
            this.match = choice;
        }
      
    }
    public boolean evaluateProposal(Person proposer) {
        if ((!this.isEngaged)||(list.getWeight(match.getName())  
                < list.getWeight(proposer.getName()))){
            if (this.isEngaged) match.isEngaged = false;
            this.isEngaged = true;
            match = proposer;
            return true;
        } 
        else return false;
            
    }   
}
class llPreferences {
   private int size;
   private PreferenceNode head;
   private PreferenceNode tail;
   
   public llPreferences() {
       head = null;
       tail = null;
       size = 0;
   }
   class PreferenceNode {
      private Person value;
      private PreferenceNode link;
      public PreferenceNode(Person value) {
        this.value  = value;
        link        = null;
      }
      private PreferenceNode () {}
      public void setValue(Person value) {this.value = value;}
      public Person getValue() {return value;}
      public void setLink(PreferenceNode link) {this.link = link;}
      public PreferenceNode getLink() {return link;}
   }
   
   public void addLast(Person p) {
       PreferenceNode temp;
       temp = new PreferenceNode(p);
       if (size == 0) head = temp;
       else tail.setLink(temp);
       tail = temp;
       size++;
   }
   public void listElements() {
       PreferenceNode i;
       i = head;
       while (i != null) {
           GuiStableMarriage.Output.appendText(i.getValue().getName() + "\n");
           i = i.getLink();
       }
   }
   public int getWeight(String name) {
       PreferenceNode i;
       int index;
       index = size;
       i = head;
       while (i!= null) {
           if (i.getValue().getName().equals(name)) return index;
           else {
               index--;
               i = i.getLink();
           }
       }
       return -1;
   }
   public Person getFirst() {
       Person a;
       a = head.getValue();
       head = head.getLink();
       return a;
   }
}
