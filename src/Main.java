import java.util.*;
public class Main {
    public String input;
    public Node head =null;
    Main(String input){
        this.input = input;
        this.head = parsingFunction(this.input);
    }
    public Node parsingFunction(String in){
        String line = in;
        StringTokenizer myTokens = new StringTokenizer(line);
        Node head = null, previous = null;
        int i = 0;
        while (myTokens.hasMoreTokens()) {
            Node current = new Node();
            String term = myTokens.nextToken();
            if (term.startsWith("+"))
                term = term.substring(1);

            current.factor = Integer.parseInt(
                    term.substring(0, term.indexOf("x")));
            current.exponent = Integer.parseInt(
                    term.substring(term.indexOf("^") + 1));

            if (previous == null)
            {
                head = current;
                previous = head;
            } else
            {
                previous.next = current;
                previous = current;
            }
        }
        return head;

    }

    private static Node addPolynomials(Node first, Node second) {
        Node head = null, current = null;
        while (null!=first || null!=second)
        {
            boolean pickfirst = false;
            boolean haveBoth = (null!=first && null!=second);

            Node node;
            if (haveBoth && first.exponent == second.exponent)
            {
                node = new Node(first.factor + second.factor, first.exponent, null);
                first = first.next;
                second = second.next;
            } else
            {
                pickfirst = first!=null &&
                        ((second == null) || first.exponent > second.exponent);

                if (pickfirst)
                {
                    node = new Node(first.factor, first.exponent, null);
                    first = first.next;
                } else
                {
                    node = new Node(second.factor, second.exponent, null);
                    second = second.next;
                }
            }

            if (current == null)
            {
                head = node;
                current = head;
            } else
            {
                current.next = node;
                current = node;
            }
        }

        return head;
    }

    private static Node subPolynomials(Node first, Node second) {
        Node head = null, current = null;
        while (null!=first || null!=second)
        {
            boolean pickfirst = false;
            boolean haveBoth = (null!=first && null!=second);

            Node node;
            if (haveBoth && first.exponent == second.exponent)
            {
                    node = new Node(first.factor - second.factor, first.exponent, null);
                    first = first.next;
                    second = second.next;

            } else
            {
                pickfirst = first!=null &&
                        ((second == null) || first.exponent > second.exponent);

                if (pickfirst)
                {
                    node = new Node(first.factor, first.exponent, null);
                    first = first.next;
                } else
                {
                    node = new Node(second.factor, second.exponent, null);
                    second = second.next;
                }
            }

            if (current == null && node.factor != 0)
            {
                head = node;
                current = head;
            } else if(node.factor != 0 )
            {
                current.next = node;
                current = node;
            }
        }

        return head;
    }

    private static Node mulPolynomials(Node list1, Node list2) {
        Node temp1= list1;
        Node temp2= list2;
        Node front=null;
        Node last=null;
        while(temp1!=null) {
            if(temp2==null) {
                temp2=list2;
            }
            while(temp2!=null) {
                Node ptr = new Node((temp1.factor*temp2.factor),(temp1.exponent+temp2.exponent), null);
                if(last!=null) {
                    last.next=ptr;
                }
                else {
                    front=ptr;
                }
                last=ptr;
                temp2=temp2.next;
            }
            temp1=temp1.next;
        }

        return front;
    }

    private static void divPolynomials(Node dividend, Node divisor) {
        Node head = null, current = null;
        while(dividend!= null && divisor != null &&  dividend.exponent >= divisor.exponent) {
            Node node = new Node();
            node.factor = dividend.factor / divisor.factor ;
            node.exponent = dividend.exponent - divisor.exponent;
            System.out.println(node);
            if(head == null) {
                head = node;
                current = head;
            }
            else {
                current.next = node;
                current = node;
            }
            Node intermediatePoly = mulPolynomials(head,divisor);
            printList(intermediatePoly);
            Node remainder = subPolynomials(dividend, intermediatePoly);
            printList(remainder);
            dividend = remainder;
        }
        if(dividend.factor != 0){

           printList(head);
           printList(dividend);
           System.out.print(" / ");
           printList(divisor);
        }

        else
            printList(head);
    }

    private static void printList(Node head) {
        for (Node ptr = head; ptr != null; ptr = ptr.next)
            System.out.print(ptr);
        System.out.println();
    }
    public static void main(String[] args) {
        Main obj1 = new Main("1x^3 +4x^2 +2x^1 -3x^0");
        Main obj2 = new Main("4x^3 +2x^2 +1x^1 +2x^0");
       // Node addHead = addPolynomials(obj1.head, obj2.head);
       // Node subHead = subPolynomials(obj1.head, obj2.head);
        Node mulHead = mulPolynomials(obj1.head, obj2.head);
        //divPolynomials(obj1.head,obj2.head);
        //printList(obj1.head);
        //printList(obj2.head);
        //printList(addHead);
       // printList(subHead);
        printList(mulHead);

    }
}
