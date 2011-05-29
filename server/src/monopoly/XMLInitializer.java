package monopoly;

import generated.jaxb.CardBase;
import generated.jaxb.CityType;
import generated.jaxb.GetOutOfJailCard;
import generated.jaxb.GotoCard;
import generated.jaxb.MonetaryCard;
import generated.jaxb.Monopoly;
import generated.jaxb.SimpleAssetType;
import generated.jaxb.SquareBase;
import generated.jaxb.SquareType;

import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import squares.GoToJailSquare;
import squares.JailSlashFreePassSquare;
import squares.ParkingSquare;
import squares.Square;
import squares.StartSquare;
import squares.SurpriseCardSquare;
import squares.WarrantCardSquare;
import assets.Asset;
import assets.City;
import assets.Country;
import assets.UtilOrTranspoAsset;
import assets.UtilOrTranspoAssetGroup;
import cards.ActionCard;
import cards.ShaffledDeck;
import java.io.InputStream;

public class XMLInitializer implements MonopolyInitilizer {

    generated.jaxb.Monopoly monopolyCollection;
   /**
     * a constructor for the XML initilizer
     */
    public XMLInitializer() {
        InputStream xmlFile = getClass().getClassLoader().getResourceAsStream(GameManager.DataFile);
        JAXBContext jc;
        Unmarshaller unmarshaller;
        try {
            jc = JAXBContext.newInstance("generated.jaxb", getClass().getClassLoader());
            unmarshaller = jc.createUnmarshaller();
            monopolyCollection = (Monopoly) unmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            throw new RuntimeException("Error entering JAXB API!");
        }
    }

    @Override
    public ArrayList<Square> initBoard() {
        UtilOrTranspoAssetGroup utilities = initUtilities(); //Inits the utils assets, and returns the group, which is a list.
        UtilOrTranspoAssetGroup transportation = initTransportation(); //Inits the transportation assets, and returns the group, which is a list.
        ArrayList<City> gameCities = getCitiesList(initCountries()); //Inits the countries and cities, and returns a sequential list of cities.
        ArrayList<Square> gameBoard = new ArrayList<Square>(GameManager.NUMBER_OF_SQUARES);

        int utilsIndex = 0, transpoIndex = 0, cityIndex = 0, boardIndex = 0;

        for (JAXBElement<? extends SquareBase> sb : monopolyCollection.getBoard().getContent()) {
            if (sb.getDeclaredType().getSimpleName().equalsIgnoreCase("startSquareType")) {
                gameBoard.add(boardIndex, new StartSquare());
            } else if (sb.getDeclaredType().getSimpleName().equalsIgnoreCase("jailSlashFreeSpaceSquareType")) {
                gameBoard.add(boardIndex, new JailSlashFreePassSquare());
            } else if (sb.getDeclaredType().getSimpleName().equalsIgnoreCase("parkingSquareType")) {
                gameBoard.add(boardIndex, new ParkingSquare());
            } else if (sb.getDeclaredType().getSimpleName().equalsIgnoreCase("gotoJailSquareType")) {
                gameBoard.add(boardIndex, new GoToJailSquare());
            } else if (sb.getDeclaredType().getSimpleName().equalsIgnoreCase("squareType")) {
                String type = ((SquareType) sb.getValue()).getType();
                if (type.equalsIgnoreCase("CITY")) {
                    gameBoard.add(boardIndex, gameCities.get(cityIndex));
                    cityIndex++;
                } else if (type.equalsIgnoreCase("UTILITY")) {
                    gameBoard.add(boardIndex, utilities.get(utilsIndex));
                    utilsIndex++;
                } else if (type.equalsIgnoreCase("TRANSPORTATION")) {
                    gameBoard.add(boardIndex, transportation.get(transpoIndex));
                    transpoIndex++;
                } else if (type.equalsIgnoreCase("SURPRISE")) {
                    gameBoard.add(boardIndex, new SurpriseCardSquare());
                } else //Here be "WARRANT" - arrrrgh
                {
                    gameBoard.add(boardIndex, new WarrantCardSquare());
                }
            }
            boardIndex++;
        }
        return gameBoard;
    }

    @Override
    public ShaffledDeck initSurprise() {
        ShaffledDeck surprise = new ShaffledDeck();
        // read surprise cards
        for (CardBase cb : monopolyCollection.getSurprise().getSurpriseCards()) {
            surprise.add(cardFactory(cb, true));
        }
        return surprise;
    }

    @Override
    public ShaffledDeck initCallUp() {
        ShaffledDeck callUp = new ShaffledDeck();
        // read call-up cards
        for (CardBase cb : monopolyCollection.getWarrant().getWarrantCards()) {
            callUp.add(cardFactory(cb, false));
        }
        return callUp;
    }

    private ActionCard cardFactory(CardBase cb, boolean isSurpriseCard) {
        ActionCard currentCard = null;
        GameManager.AgainstWho against = (cb.getNum() == 1) ? GameManager.AgainstWho.OtherPlayers : GameManager.AgainstWho.Treasury;
        String text = cb.getText();
        int cardType;
        if (isSurpriseCard) {
            cardType = ActionCard.SURPRISE_CARD;
        } else {
            cardType = ActionCard.CALLUP_CARD;
        }
        if (cb instanceof MonetaryCard) {
            MonetaryCard inputCard = (MonetaryCard) cb;
            text = text.replaceAll("%s", inputCard.getSum() + "");
            currentCard = new ActionCard(cardType, text, (int) inputCard.getSum(), against, null, isSurpriseCard);
        } else if (cb instanceof GotoCard) {
            GotoCard inputCard = (GotoCard) cb;
            String gotoLocation = inputCard.getTo();
            Class<? extends Square> whereTo = null;
            if (gotoLocation.toUpperCase().equals("NEXT_WARRANT")) {
                whereTo = WarrantCardSquare.class;
            } else if (gotoLocation.toUpperCase().equals("NEXT_SURPRISE")) {
                whereTo = SurpriseCardSquare.class;
            } else if (gotoLocation.toUpperCase().equals("START")) {
                whereTo = StartSquare.class;
            } else //Then --> (gotoLocation.toUpperCase().equals("JAIL"))
            {
                whereTo = GoToJailSquare.class;
            }
            currentCard = new ActionCard(cardType, text, 0, against, whereTo, isSurpriseCard);
        } else if (cb instanceof GetOutOfJailCard) {
            currentCard = new ActionCard(cardType, text, 0, against, Square.class, isSurpriseCard);
        } else {//Invalid card type
            throw new RuntimeException("Invalid card type!");
        }
        return currentCard;
    }

    private UtilOrTranspoAssetGroup initUtilities() {
        long stayCost = monopolyCollection.getAssets().getUtilities().getStayCost();
        UtilOrTranspoAssetGroup utilGroup = new UtilOrTranspoAssetGroup("Utilities", (int) stayCost);
        for (SimpleAssetType util : monopolyCollection.getAssets().getUtilities().getUtility()) {
            new UtilOrTranspoAsset(utilGroup, util.getName(), (int) util.getCost(), (int) util.getStayCost());
        }
        return utilGroup;
    }

    private UtilOrTranspoAssetGroup initTransportation() {
        long stayCost = monopolyCollection.getAssets().getTransportations().getStayCost();
        UtilOrTranspoAssetGroup transpoGroup = new UtilOrTranspoAssetGroup("Transportation", (int) stayCost);
        for (SimpleAssetType transpo : monopolyCollection.getAssets().getTransportations().getTransportation()) {
            new UtilOrTranspoAsset(transpoGroup, transpo.getName(), (int) transpo.getCost(), (int) transpo.getStayCost());
        }
        return transpoGroup;
    }

    private ArrayList<Country> initCountries() {
        ArrayList<Country> countriesList = new ArrayList<Country>();
        for (generated.jaxb.Monopoly.Assets.Countries.Country currentCountry : monopolyCollection.getAssets().getCountries().getCountry()) {
            Country newCountry = new Country(currentCountry.getName());
            for (CityType currentCity : currentCountry.getCity()) {
                int[] rentPrices = {(int) currentCity.getStayCost(), (int) currentCity.getStayCost1(), (int) currentCity.getStayCost2(), (int) currentCity.getStayCost3()};
                new City(newCountry, currentCity.getName(), (int) currentCity.getCost(), (int) currentCity.getHouseCost(), rentPrices);
            }
            countriesList.add(newCountry);
        }
        return countriesList;
    }

    private ArrayList<City> getCitiesList(ArrayList<Country> countries) {
        ArrayList<City> result = new ArrayList<City>();
        for (Country country : countries) {
            for (Asset city : country) {
                result.add((City) city);
            }
        }
        return result;
    }
}
