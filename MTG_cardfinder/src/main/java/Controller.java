/* MTG Card Finder - Takes a list of cards and prints a list so one knows where to look in their sorted collection.
 * Copyright (C) 2017 Wesley Mauk
 *
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Controller {
    
    MainMenu theMainMenu;
    
    public Controller()
    {
        theMainMenu = new MainMenu(this);
        theMainMenu.setVisible(true);
        theMainMenu.setLocationRelativeTo(null);
    }

    public void requestGenerateList(ArrayList<String> theCardList, int[] amount, int size) 
    {
        // As of now, We have to grab one card at a time which might be slow for larger lists
        // the limit is 5000 requests per hour, so unless you're building a lot of decks, you 
        // should be fine.
        // NOTE: I may be wrong about grabbing one card at a time, but experimentation led to 
        //       this conclusion.
        
        
        List<String> nameFilter = new ArrayList<String>() {};
        String searchQuery;
        List<Card> cards; 
        int size1;
        ArrayList<String> skipped = new ArrayList<String>();
        
        
        try
        {
            
            FileOutputStream out = null;
            File textFile = new File("cardList.txt");
            out = new FileOutputStream(textFile);
            String temp;
        
        
            for(int c = 0; c < size; c++)
            {
                
                String originalCard = theCardList.get(c).trim();
                searchQuery = "name=" + theCardList.get(c).trim();
                nameFilter.add(searchQuery);
                
                // Calls the API and gets the list of cards that match the query
                cards = CardAPI.getAllCards(nameFilter);
                size1 = cards.size();
                
                               
                if(size1 == 0)
                {
                    String reason = searchQuery.substring(5);
                    skipped.add(reason);
                    continue;
                }
                
                String rarity = "Unchecked";
                boolean flagSkip = false;
                
                for(int i = 0; i < size1; i++)    // check rarity and skip if not common or uncommon                 
                {
                    
                    // check to see if we're working on the card that we searched for
                    if(!originalCard.equalsIgnoreCase(cards.get(i).getName()))
                    {
                        continue;
                    }
                    
                    String tempRarity = cards.get(i).getRarity();
                    if (tempRarity.equalsIgnoreCase("Common"))
                    {
                        rarity = "Common";
                        flagSkip = false;
                        break;
                    }
                    else if (tempRarity.equalsIgnoreCase("Uncommon"))
                    {
                        rarity = "Uncommon";
                        flagSkip = false;
                        break;
                    }
                    else
                    {
                        flagSkip = true;
                    }
                    
                    
                }
                
                if (!flagSkip)
                {
                    
                    temp = amount[c] + " " + originalCard + " ";        // print quantity and card name

                    for(int i = 0; i < size1; i++)                     // print sets printed in
                    {
                        temp = temp + cards.get(i).getSet() + " ";
                    }
                    temp = temp + rarity + System.lineSeparator();     // prints rarity and newline

                    //System.out.println(temp);
                    byte[] finalTemp = temp.getBytes();

                    out.write(finalTemp);
                
                }
                else
                {
                    temp = cards.get(0).getName();
                    skipped.add(temp);
                }
            }
            
            // print skipped cards
            String skippedString = System.lineSeparator() + "Skipped Cards:" + System.lineSeparator();
            
            for(int c = 0; c < skipped.size(); c++)
            {
                skippedString = skippedString + skipped.get(c) + System.lineSeparator();
            }
            
            byte[] skippedByte = skippedString.getBytes();
            out.write(skippedByte);
            
            out.close();
            
            JFrame frame = new JFrame();
            //custom title, warning icon
            JOptionPane.showMessageDialog(frame, "All done!");
            
            System.exit(0);
            
            
        
        }
        catch(Exception e){
            e.printStackTrace();
            // popup something went wrong
            JFrame frame = new JFrame();
            //custom title, warning icon
            JOptionPane.showMessageDialog(frame, "Something went wrong with making the card list!", "Error", JOptionPane.WARNING_MESSAGE);
        }
        
    }
    
}
