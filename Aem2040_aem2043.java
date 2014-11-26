/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aem2040_aem2043;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/*ONOMATA 
ΝΙΚΟΣ ΓΕΩΡΓΙΑΔΗΣ ΑΕΜ 2043
ΔΗΜΗΤΡΗΣ ΑΓΤΖΙΔΗΣ ΑΕΜ 2040 */


/*Η πολυπλοκτητα του συγκεκριμενου προβληματος ειναι ψευδοπολυωνυμικη διοτι αν και εξαρταται πολυωνυμικα απο το μεγεθος του στιγμιοτυπου προβληματος,
εξαρταται και απο το μεγεθος του αριθμου maxCapacity που εμφανιζεται στο στιγμιοτυπο Ο(numberofObjects*maxCapacity) */

public class Aem2040_aem2043 {

    static int dynamicProgrammingUnbounded(int maxCapacity, int numOfObjects, int objects[][], int table[][]) {
      
       
        int m = -1; // σαν δεικτης για το ποιο ειδος κουτιου χρησιμοποιουμε


        //  με επαναληπτικη μεθοδο για ολες τις θεσεις του πινακα χρησιμοποιουμε τον παρακατω τυπο για να συμπληρωσουμε τα κελια του
        // πινακα και να βρουμε το μικροτερο αριθμο κουτιων που χωρανε στο  container .O αριθμος αυτος βρισκεται στο τελευταιο κελι του πινακα.



        //   3)          _   table[i-1,j]                         Εαν η χωρητικοτητα  του συγκεκριμενου κουτιου ειναι μικροτερη απο την συνολικη χωρητικοτητα
//                   |                                          του container τοτε ή θα το προσθεσουμε στο συνολο των κουτιων ή οχι(δλδ κραταμε το προηγουμε συνολο κουτιων.
        //       min |                                  vi<=j   Αναλογα με το ποια επιλογη μας δινει το μικροτερο πληθος κουτιων για την δεδομενη χωρητικοτητα του container
        //           | _   1 + ([i,j-vi]

        // table =                                            Εαν η χωρητικοτητα  του συγκεκριμενου κουτιου ειναι μεγαλυτερη απο την συνολικη χωρητικοτητα
        //     4)                                                 του container τοτε δεν θα το συμπεριλαβουμε στο συνολο των κουτιων και θα χρησιμοποησουμε το προηγουμενο συνολο  
        //          table[i-1,j]                        vi>j



        // 1) Την πρωτη στηλη του πινακα την συμπληρωνουμε με 0 αφου οταν η χωρητικοτητα του containerειναι 0 τοτε οσα κουτια και να εχεις 
        //   δεν αλλαζει κατι .
        // 2) Την πρωτη γραμμη του πινακα την συμπληρωνουμε με οο (στην συγκεκριμενη περιπτωση χρησιμοποιησα ενα τυχαιο μεγαλο αριθμο 900000)
        //  αφου οταν ο αριθμος των κουτιων ειναι 0 τοτε ειναι αδυνατο να γεμισουμε το container.

        for (int i = 0; i <= numOfObjects; i++) {
            for (int j = 0; j <= maxCapacity; j++) {

                if (j == 0) {    //   (1)

                    table[i][j] = 0;

                } else if (i == 0) {   //   (2) 
                    table[0][j] = 900000;


                } else if (objects[m][1] > j) {     //  (4)
                    table[i][j] = table[i - 1][j];

                } else {                //  (3)
                    table[i][j] = Math.min(table[i - 1][j], table[i][j - objects[m][1]] + 1);
                }

                if (i == 0 && j == 0) {  
                    table[i][j] = 0;
                }
            }
            m++;
        }

        return table[numOfObjects][maxCapacity]; //επιστρεφεται το τελευταιο στοιχειο του πινακα οπου ειναι και η λυση δλδ το μικροτερο πληθος κουτιων
    }
    
        //ακολουθουμε την ιδια διαδικασια οπως και στο unbounded case μονο που τωρα θελουμε να χρησιμοποιησουμε το καθε ειδος κουτιου μονο μια φορα 
    //αυτο υλοποιειται με Math.min (table[i - 1][j - temp[m - 1][1]] + temp[m - 1][2]) οπου οταν επιλεγουμε να συμπεριλαβουμε στην λυση το εκαστοτε κουτι μετα 
    //το αφαιρουμε απο την χωρητικοτητα του container και παμε στην ι-1 θεση αυτη τη φορα και οχι στην ι οπως στο unbounded case γιατι 
    //δεν θελουμε να ξαναχρησιμοποιησουμε  ενα ιδιο κουτι παραπανω απο μια φορα.Επισης  + temp[m - 1][2] και οχι +1 οπως ειχαμε πριν 
    //γιατι το καθε κουτι μπορει να αντιστοιχει σε πολλα κουτια και οχι μονο σε ενα.
    static int dynamicProgrammingBounded(int maxCapacity, int numOfObjects, int objects[][], int table[][], int temp[][], int count) {

        int m = 0;
        for (int i = 0; i <= count; i++) {
            for (int j = 0; j <= maxCapacity; j++) {

                if (j == 0) {

                    table[i][j] = 0;

                } else if (i == 0) {
                    table[0][j] = 900000;


                } else if (temp[m - 1][1] > j) {
                    table[i][j] = table[i - 1][j];

                } else {

                    table[i][j] = Math.min(table[i - 1][j], table[i - 1][j - temp[m - 1][1]] + temp[m - 1][2]);

                }

                if (i == 0 && j == 0) {
                    table[i][j] = 0;
                }
            }
            m++;
        }

        return table[count][maxCapacity]; //επιστρεφουμε την τελευταια θεση του πινακα οπου ειναι και η λυση

    }

    static void printSolutionUnbounded(int table[][], int sum[], int numOfObjects, int maxCapacity, int objects[][]) {
        int i = numOfObjects;   // ξεκιναμε απο την τελευταια θεση του πινακα και ανεβαινουμε προς τα πανω συκρινοντας την ι-1 θεση με την ι.  table[i][j] != table[i - 1][j]
        int j = maxCapacity;    // εαν ειναι διαφορετικο το στοιχειο αυτο σημαινει οτι το ι κουτι χρησιμοποιθηκε στη λυση επομενος θα το κρατησουμε σε εναν πινακα 
        while (i != 0) {        // sum[i].Στην συνεχεια θα ελενξουμε εαν το συγκεκριμενο κουτι εχει χρησιμοποιηθει παραπανω απο μια φορες με τον τυπο
                                //   j = j - objects[i - 1][1];  δλδ αφαιρουμε το κουτι και κανουμε παλι την ιδια διαδικασια
            if (table[i][j] != table[i - 1][j]) {    
                sum[i]++;
                j = j - objects[i - 1][1]; //βαζουμε ι-1 στο object γιατι ο table εχει μια γραμμη παραπανω λογω του οτι συμπεριλαμβανουμε και το ενδεχομενο να εχουμε 0 κουτια 
            } else {        //εαν δεν ειναι διαφορετικο τοτε απλα παμε στο προηγουμενο αντικειμενο και κανουμε την ιδια διαδικασια
                i--;
            }
        }

        for (int k = 1; k <= numOfObjects; k++) {

            System.out.println("Object " + k + " is used " + sum[k] + " times ");

        }
    }

    static void printSolutionBounded(int table[][], int sum[], int maxCapacity, int temp[][], int count, int objects[][], int numOfObjects) {
        int i = count;
        int j = maxCapacity;
                    //σε αυτο το σημειο ακολουθω ακριβως την ιδια διαδικασια οπως και πριν 
        while (j != 0) {


            if (table[i][j] != table[i - 1][j]) {
                sum[i] = 1;
                j = j - temp[i - 1][1]; //βαζουμε ι-1 στο temp γιατι ο table εχει μια γραμμη παραπανω λογω του οτι συμπεριλαμβανουμε και το ενδεχομενο να εχουμε 0 κουτια  
            }
            i--;
        }

        int[] sum_1 = new int[numOfObjects]; //σε αυτο τον πινακα συγκεντωνουμε ολα τα νεα κουτια που δημιουργηθηκαν και ανηκουν σε ενα ειδος κουτιου και εχουν 
        for (int m = 0; m < numOfObjects; m++) { //χρησιμοποιηθει στην λυση  και τα προσθετουμε  πχ 2 κουτια που ανηκουν στην λυση και ειναι απο το 1ο ειδος κουτιου
            for (int n = 0; n < count; n++) { // sum_1[0]= ποσοτητα 1ο νεου κουτιου + ποσοτητα 2ο νεου κουτιου 
                if (temp[n][0] == objects[m][0] && sum[n + 1] == 1) { //sum[n + 1] == 1 σημαινει οτι το κουτι εχει 
                    sum_1[m] = sum_1[m] + temp[n][2]; //χρησιμοποιηθει στην λυση

                }
            }
        }

        for (int m = 0; m < numOfObjects; m++) {
            System.out.println("Object " + objects[m][0] + " is used " + sum_1[m] + " times ");
        }


    }

    static boolean hasSolution(int x) { // στην συναρτηση αυτη περναμε ως παραμετρο την τελευταια θεση του table και αν ειναι 900.000 που δηλωνει το αδυνατο
        if (x == 900000) {              //αυτο σημαινει οτι ειτε η συνολικη χωρητικοτητα των κιβωτιων ειναι μικροτερη του container ή μεγαλυτερη αρα δεν υπαρχει λυση
            return false;
        } else {         //αλλιως σημαινει οτι υπαρχει λυση και το container γεμιζει ακριβως
            return true;
        }

    }

    public static void main(String[] args) throws FileNotFoundException {

        String InputPath = args[0];  // location of input file
        String[] InputValues;       // a parsed input line that contains box id - volume - quantity

        int maxCapacity = 0;       // capacity of the container (in volume units)
        int numOfObjects = 0;        // number of distinct objects        
        int[][] objects = null;    // objects[i][0]: the id of the i-th box, typically it is i+1
        // objects[i][1]: the volume of the i-th box
        // objects[i][2]: the maximum quantity of the i-th object
        int[][] table = null;      // the table used in the dynamic programming algorithm
        // [....your own variables....] 


        //reading the input file
        try {
            BufferedReader br = new BufferedReader(new FileReader(InputPath));
            maxCapacity = Integer.parseInt(br.readLine());
            numOfObjects = Integer.parseInt(br.readLine());
            String line = br.readLine(); // skip empty line

            objects = new int[numOfObjects][3];
            line = br.readLine();        //move to next line
            for (int j = 0; j < numOfObjects; j++) {
                InputValues = line.split(" ");
                for (int i = 0; i < 3; i++) {
                    objects[j][i] = Integer.parseInt(InputValues[i]);
                }
                line = br.readLine();
            }

            table = new int[numOfObjects + 1][maxCapacity + 1]; // ο πινακας για τον δυναμικο προγραμματισμο με μεγεθος γραμμων
            //οσο ειναι το ειδος των κουτιων +1 αφου μετραμε και την περιπτωση οπου δεν εχουε κουτια 
            //και μεγεθος στηλων οσο ειναι η χωρητικοτητα του container + 1 αφου μετραμε και την περιπτωση οπου η χωρητικοτητα ειναι 0 .                                                  
            //printing the input
            System.out.println("The total container capacity is " + maxCapacity + " and there are " + numOfObjects + " box types as follows:");
            System.out.println("id \t volume \t quantity");
            System.out.println("====================================================");
            for (int i = 0; i < numOfObjects; i++) {
                System.out.println(objects[i][0] + "\t" + objects[i][1] + "\t\t" + objects[i][2]);
            }

            //UNBOUNDED CASE

            //[put your code here]





            int x = dynamicProgrammingUnbounded(maxCapacity, numOfObjects, objects, table); //give any arguments you like here!
            int[] sum = null;
            sum = new int[numOfObjects + 1]; //αποθηκευει τις φορες που εχει χρησιμοποιθει στην λυση καθε ειδος κουτιου
            //[put your code here]

            System.out.println("\nUnbounded case (i.e., ignoring quantities)");
            System.out.println("============================================");
            if (hasSolution(x)) { //give any arguments you like here!                    
                System.out.println("The minimum number of boxes is " + x);
                printSolutionUnbounded(table, sum, numOfObjects, maxCapacity, objects); //give any arguments you like here! 
            } else {
                System.out.println("\nThere is no solution");
            }


            //BOUNDED CASE    !!!!!!!!!!!!!!!!!!!!!

            //[put your code here]

            int[][] temp = null; // ειναι ο αντιστοιχος πινακας του object .  [ι][0] id του κουτιου [ι][1] χωρητικοτητα κουτιου [ι][2] ποσοτητα κουτιου
            sum = null; // αποθηκευει τις φορες που εχει χρησιμοποιθει στην λυση καθε ειδος κουτιου

            int a;  // απο τον τυπο που μας δινεται α= logm
            int count = 0; //μετραει ποσα  κουτια δημιουργουνται.  ειναι το αντιστοιχο numofObjects αλλα για το  bounded case
            double d;  //απο τον τυπο που μας δινεται d= m -2^a +1
            int s = 0; //εξηγειται παρακατω σε τι βοηθαει
            int metritis = 0;
            temp = new int[1000][3]; 
            for (int i = 0; i < numOfObjects; i++) { //για καθε ενα ειδος κουτιου βρισκουμε το δικο του α και d. 
                s = 0;               
                metritis = 0; //μετραει το ποσα νεα  κουτια δημιουργουνται για καθε ειδος κουτιου 
                
                a = (int) (Math.log(objects[i][2]) / Math.log(2));  //α= logm

                d = objects[i][2] - Math.pow(2, a) + 1;   //d= m -2^a +1

                while (s != (objects[i][1] * objects[i][2])) {
                    if (count==1000) { //δυναμικη διαχειριση μνημης σε περιπτωση που τα νεα κουτια ειναι περισσοτερα απο 1000
                        int[][] resized_array=new int [temp.length+100000][3];
                        System.arraycopy(temp , 0, resized_array, 0, temp.length);
                    }
                    
                    // συμφωνα με τον τυπο που μας δοθηκε για να μην μας βγει εκθετικη η πολυπλοκοτητα,
                        // τα νεα κουτια που θα δημιουργηθουν απο τα υπαρχοντα κουτια , θα εχουν χωρητικοτητα V ,2V ,2^2V .. 2^a-1 V ,dV 
                     //ara  to loop (while )  θα σταματησει οταν  η συνολικη χωρητικοτητα των νεων κουτιων γινει ιση με την αρχικη χωρητικοτητα ( για ενα ειδος κουτιου)
                    // πχ V=24 m=20   αρχικη χωρητικοτητα 24*20=480
                    //θα δημιουργηθει : 1 κουτι με V=24
                    //                  2 koutia me V=48 to kathena
                    //                  4 κουτια με V=96 το καθενα
                    //                  8 κουτια με V=192 το καθενα
                    //                  5 κουτια με V=120 το καθενα  
                    // συνολικη χωηρητικοτητα = 24+48 +96+192 +120=480 
                    temp[count][0] = i + 1;  // to id του καθε νεου κουτιου πχ για το παραπανω παραδειγμα το id ολων αυτων των νεων κουτιων ειναι 1 
                                             //αφου ανηκουν στο πρωτο κουτι
                    //εαν ο μετρητης γινει μεγαλυτερος του α-1 αυτο σημαινει οτι για  να βρω την χωρητικοτητα αυτου του κουτιου θα πρεπει να 
                    if (metritis > a - 1) {  // πολλαπλασιασω με το d ( συμφωνα με τον τυπο) 
                        temp[count][1] = (int) (d * objects[i][1]); // η xwritikotita του καθε νεου κουτιου
                        temp[count][2] = (int) d; //ο αριθμος που δειχνει σε ποσα κουτια αντιστοιχει το νεο κουτι

                       
                    } else { //εαν ο μετρητης ειναι μικροτερος τοτε βρισκω την χωρητικοτητα και το σε ποσα κουτια αντιστοιχειο το καθε νεο κουτι συμφωνα με τον τυπο που μας δοθηκε
                        temp[count][1] = (int) (Math.pow(2, metritis) * objects[i][1]); //// η xwritikotita του καθε νεου κουτιου
                        temp[count][2] = (int) Math.pow(2, metritis);//ο αριθμος που δειχνει σε ποσα κουτια αντιστοιχει το νεο κουτι
                    }
                    s = s + temp[count][1]; //προσθετουμε την καινουργια χωρητικοτητα του καθε νεου κουτιου
                    count++;
                    metritis++; 


                }

            }

            table = new int[count + 1][maxCapacity + 1]; // ο πινακας για τον δυναμικο προγραμματισμο

            x = dynamicProgrammingBounded(maxCapacity, numOfObjects, objects, table, temp, count); //give any arguments you like here!

            sum = new int[count + 1];   // αποθηκευει τις φορες που εχει χρησιμοποιθει στην λυση καθε ειδος κουτιου
            //[put your code here] 

            System.out.println("\nΒounded case (i.e., considering quantities)");
            System.out.println("============================================");
            if (hasSolution(x)) { //give any arguments you like here!                    
                System.out.println("The minimum number of boxes is " + x);
                printSolutionBounded(table, sum, maxCapacity, temp, count, objects, numOfObjects); //give any arguments you like here! 
            } else {
                System.out.println("\nThere is no solution");
            }

        } catch (IOException e) {//Catch exception if any
            System.err.println("Error: " + e);
        }
        finally {
            if ( new FileReader(InputPath)!= null) {  //κλεισε το αρχειο αν υπαρχει
                try {
                    new FileReader(InputPath).close();
                } catch (IOException e) {
                    System.err.println("Error: " + e);
                }
            }
        }

    } //end of main
}
