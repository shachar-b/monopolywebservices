package src.client;

import generated.jaxb.CityType;
import generated.jaxb.Monopoly;
import generated.jaxb.SimpleAssetType;
import generated.jaxb.SquareBase;
import generated.jaxb.SquareType;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import src.squares.*;
import java.io.InputStream;
import src.assets.Asset;
import src.assets.City;
import src.assets.Country;
import src.assets.UtilOrTranspoAsset;
import src.assets.UtilOrTranspoAssetGroup;

public class XMLInitializer {

    generated.jaxb.Monopoly monopolyCollection;
    private final int NUMBER_OF_SQUARES = 36;

    public XMLInitializer() {
        String xmlData = Server.getInstance().getGameBoardXML();
        InputStream xmlFile = new ByteArrayInputStream(xmlData.getBytes());
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

    public ArrayList<Square> initBoard() {
        UtilOrTranspoAssetGroup utilities = initUtilities(); //Inits the utils assets, and returns the group, which is a list.
        UtilOrTranspoAssetGroup transportation = initTransportation(); //Inits the transportation assets, and returns the group, which is a list.
        ArrayList<City> gameCities = getCitiesList(initCountries()); //Inits the countries and cities, and returns a sequential list of cities.
        ArrayList<Square> gameBoard = new ArrayList<Square>(NUMBER_OF_SQUARES);

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
                    gameBoard.add(boardIndex, new ActionCardSquare(1));
                } else //Here be "WARRANT" - arrrrgh
                {
                    gameBoard.add(boardIndex, new ActionCardSquare(-1));
                }
            }
            boardIndex++;
        }
        return gameBoard;
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
