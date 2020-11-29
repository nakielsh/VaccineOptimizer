package vaccine.file;

import org.junit.Test;
import vaccine.exceptions.*;
import vaccine.objects.Connection;
import vaccine.objects.Manufacturer;
import vaccine.objects.Pharmacy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigurationIOTest {
    private final ConfigurationIO configurationIO = new ConfigurationIO();

    @Test(expected = ExistingIdException.class)
    public void shouldThrowExistingIdExceptionWhenThereAreTheSameIDs() throws Exception {
        //given
        String path = "./to_test/sameID.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        assert false;
    }

    @Test(expected = ExistingNameException.class)
    public void shouldThrowExistingNameExceptionWhenThereAreTheSameNames() throws Exception {
        //given
        String path = "./to_test/sameNames.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        assert false;
    }

    @Test(expected = FileStructureException.class)
    public void shouldThrowFileStructureExceptionWhenThereAreWrongHeaders() throws Exception {
        //given
        String path = "./to_test/wrongHeaders.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        assert false;
    }

    @Test(expected = FileStructureException.class)
    public void shouldThrowFileStructureExceptionWhenThereIsWrongArgumentsNumber() throws Exception {
        //given
        String path = "./to_test/wrongArgumentsNumber.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        assert false;
    }


    @Test(expected = FileStructureException.class)
    public void shouldThrowFileStructureExceptionWheIdIsNegative() throws Exception {
        //given
        String path = "./to_test/negativeID.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        assert false;
    }

    @Test(expected = FileStructureException.class)
    public void shouldThrowFileStructureExceptionNeedIsNegative() throws Exception {
        //given
        String path = "./to_test/negativeNeed.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        assert false;
    }

    @Test(expected = FileStructureException.class)
    public void shouldThrowFileStructureExceptionPriceIsNegative() throws Exception {
        //given
        String path = "./to_test/negativePrice.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        assert false;
    }


    @Test(expected = WrongConnectionNumberException.class)
    public void shouldThrowWrongConnectionNumberExceptionWhenThereAreLessConnections() throws Exception {
        //given
        String path = "./to_test/lessConnections.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        assert false;
    }

    @Test(expected = WrongConnectionNumberException.class)
    public void shouldThrowWrongConnectionNumberExceptionWhenThereAreMoreConnections() throws Exception {
        //given
        String path = "./to_test/moreConnections.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        assert false;
    }

    @Test(expected = HigherNeedThanProductionException.class)
    public void shouldThrowHigherNeedThanProductionExceptionWhenManCantProvideVaccineNumberExpected() throws Exception {
        //given
        String path = "./to_test/higherNeed.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        assert false;
    }

    @Test(expected = ConfigurationImpossibleToCreateException.class)
    public void shouldThrowConfigurationImpossibleToCreateExceptionWhenPharmacyNeedIsTooHigh() throws Exception {
        //given
        String path = "./to_test/higherPharmacyNeed.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        assert false;
    }

    @Test
    public void shouldCreate3PharmaciesAnd3ManufacturersWhenGivenData1() throws Exception {
        //given
        String path = "./to_test/data1.txt";
        int pharmacyNum = 0;
        int manufacturerNum = 0;
        //when
        configurationIO.loadFromFile(path);
        for (Pharmacy pharmacy : configurationIO.getPharmacyList()) {
            pharmacyNum += 1;
        }
        for (Manufacturer manufacturer : configurationIO.getManufacturerList()) {
            manufacturerNum += 1;
        }

        //then
        assertEquals(3, pharmacyNum);
        assertEquals(3, manufacturerNum);
    }

    @Test
    public void shouldCreate3ConnectionsForEveryPharmacyWhenGivenData1() throws Exception {
        //given
        String path = "./to_test/data1.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        for (Pharmacy pharmacy : configurationIO.getPharmacyList()) {
            int connectionNumber = 0;
            for (Connection connection : pharmacy.getConnectionList()) {
                connectionNumber += 1;
            }
            assertEquals(3, connectionNumber);
        }
    }

    @Test
    public void shouldCreate358PharmaciesAnd129ManufacturersWhenGivenBigData() throws Exception {
        //given
        String path = "./to_test/bigData.txt";
        int pharmacyNum = 0;
        int manufacturerNum = 0;
        //when
        configurationIO.loadFromFile(path);
        for (Pharmacy pharmacy : configurationIO.getPharmacyList()) {
            pharmacyNum += 1;
        }
        for (Manufacturer manufacturer : configurationIO.getManufacturerList()) {
            manufacturerNum += 1;
        }

        //then
        assertEquals(358, pharmacyNum);
        assertEquals(129, manufacturerNum);
    }

    @Test
    public void shouldCreate129ConnectionsForEveryPharmacyWhenGivenBigData() throws Exception {
        //given
        String path = "./to_test/bigData.txt";
        //when
        configurationIO.loadFromFile(path);
        //then
        for (Pharmacy pharmacy : configurationIO.getPharmacyList()) {
            int connectionNumber = 0;
            for (Connection connection : pharmacy.getConnectionList()) {
                connectionNumber += 1;
            }
            assertEquals(129, connectionNumber);
        }
    }

}

