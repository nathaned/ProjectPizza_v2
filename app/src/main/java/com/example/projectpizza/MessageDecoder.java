/*------------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *------------------------------------------------------------------------------
 */

package com.example.projectpizza;




import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to hold and interpret data returned from the MasterServer.  
 */
public class MessageDecoder {
    
    /**
     * Variable for holding the last encoded message return from 
     * the MasterServer; Publicly accessible to other activities
     */
    static String encodedMessage = "---";
    
    /**
     * Variable for holding the decoded result of the last message return
     * from the MasterServer; Publicly accessible to other activities
     */
    static String decodedMessage = "---";


    public static final String PERFS = "save";


    /**
     * Function for getting a decoded message given an encoded message.  
     * Each team must implement this function.
     * 
     * Example:
     * decodeResponse("U__sFLeou_rktcehe!e,~"); should return a result of "Use_the_Force,_Luke!~"
     */
    public static String decodeResponse(String encodedString)
    {
        MessageDecoder.encodedMessage = encodedString;

        /*
         * Put code here to decode the encodedString and set result.
         * Also be sure to set decodedMessage to the result.
         */
        String given = encodedString;
        int [] size = findPrimes(given.length());
        //size[0] is number of rows; size[1] is number of columns
        String out = "";
        if (size[0]==-1)
        {
            MessageDecoder.decodedMessage = "***???***";
            return "-1, length: " + given.length();
        }
        for (int col=0; col<size[0]; col++)
        {
            for (int row=0; row<size[1]; row++)
            {
                out+=given.charAt( (row*size[0]) + col );
            }
        }
        MessageDecoder.decodedMessage = out;
        out += "\n"+size[0] + "x" + size[1] + " (" + given.length() + ")";

        return out;
    }



    public static int[] findPrimes(int length)
    {
        int [] found = {-1, -1};
        final ArrayList<Integer> PRIMES = new ArrayList<Integer>(Arrays.asList(
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53,
                59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113,
                127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181,
                191, 193, 197, 199, 211, 223, 227, 229));
        for (int i=0; i<PRIMES.size(); i++)
        {
            if (length % PRIMES.get(i)==0 && PRIMES.contains(length/PRIMES.get(i)))
            {
                found[0] = PRIMES.get(i);
                found[1] = length/PRIMES.get(i);
                break;
            }
        }
        return found;
    }
}
