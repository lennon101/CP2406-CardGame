import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;
import com.sun.xml.internal.ws.api.model.ExceptionType;

import java.util.Scanner;

/**
 * Created by danelennon on 29/08/2016.
 */
public class BabyNameComparison {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String[] names = new String[3];

        for (int i = 0;i<names.length;++i){
            System.out.print("enter name " + (i+1) + ": ");
            names[i] = input.next();
        }

        for (int i=0;i< names.length;++i){
            for (int j=0;j< names.length;++j){
                for (int k=0;k< names.length;++k){
                    if ((i==j) || (i==k) || (j==k)){
                        //do nothing
                    } else{
                        System.out.println(names[i] + names[j] + names[k]);
                    }
                }
            }
        }

    }
}

