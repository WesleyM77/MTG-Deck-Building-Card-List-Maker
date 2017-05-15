# MTG Deck Building Card List Maker

This is an app I wrote to make my deckbuilding take a little less time.
After building a deck, I would go card by card and write down on a notepad
how many I needed of a specific card, the name, the sets it was printed in, 
and it's rarity. I have my cards organized by set and rarity, so it was easy 
to find a card after I had made a list, but doing so took time. This app 
takes a card list (preferable directly from TappedOut.net) and creates a 
file in the root directory that has all the information I pointed out above.
It also prints the list of skipped cards. Cards can be skipped for any number 
of reasons: There was an error retrieving that card, it was rare or mythic, 
or the card name was typed wrong. The reason I skip rare or mythic cards is
because I don't have those sorted by set and rarity so it is pointless for me
to look up that information. 

# Future features

* Add a set selector before the list screen so that only owned/sorted sets will be printed on the list
* Add checkboxes so rares and/or mythics are not skipped

# Known Bugs

* Split cards don't work
* If the user add's a blank line, the app will throw an error and the list will not be made.
* If a searched card's text returns different cards, it will return the first card, which may not be the desired card.
