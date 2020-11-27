package vaccine.calculations;

import org.junit.Test;
import vaccine.file.ConfigurationIO;
import vaccine.objects.Manufacturer;
import vaccine.objects.Pharmacy;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class VAMTest {
    ConfigurationIO configurationIO = new ConfigurationIO();

    @Test
    public void shouldMeetSupplyOfEveryPharmacyWhenGivenData1() throws Exception {
        //given
        String path = "./to_test/data1.txt";
        int need = 0;
        int got = 0;
        //when
        configurationIO.loadFromFile(path);
        VAM vam = new VAM(configurationIO.getPharmacyList(), configurationIO.getManufacturerList());
        vam.minimizeCost();
        for (Pharmacy pharmacy : vam.getPharmacyList()) {
            need += pharmacy.getNeed();
            got += pharmacy.getNeed() - pharmacy.leftToLoad();
        }
        //then
        assertEquals(need, got);
    }

    @Test
    public void shouldMeetSupplyOfEveryPharmacyWhenGivenData2() throws Exception {
        //given
        String path = "./to_test/data2.txt";
        int need = 0;
        int got = 0;
        //when
        configurationIO.loadFromFile(path);
        VAM vam = new VAM(configurationIO.getPharmacyList(), configurationIO.getManufacturerList());
        vam.minimizeCost();
        for (Pharmacy pharmacy : vam.getPharmacyList()) {
            need += pharmacy.getNeed();
            got += pharmacy.getNeed() - pharmacy.leftToLoad();
        }
        //then
        assertEquals(need, got);
    }

    @Test
    public void shouldNotGoAboveManufacturersDailyProductionWhenGivenData1() throws Exception {
        //given
        String path = "./to_test/data1.txt";
        //when
        configurationIO.loadFromFile(path);
        VAM vam = new VAM(configurationIO.getPharmacyList(), configurationIO.getManufacturerList());
        vam.minimizeCost();
        //then
        for(Manufacturer manufacturer: vam.getManufacturerList()){
            assert manufacturer.leftToSell() >= 0;
        }
    }

    @Test
    public void shouldNotGoAboveManufacturersDailyProductionWhenGivenData2() throws Exception {
        //given
        String path = "./to_test/data2.txt";
        //when
        configurationIO.loadFromFile(path);
        VAM vam = new VAM(configurationIO.getPharmacyList(), configurationIO.getManufacturerList());
        vam.minimizeCost();
        //then
        for(Manufacturer manufacturer: vam.getManufacturerList()){
            assert manufacturer.leftToSell() >= 0;
        }
    }


    @Test
    public void shouldMeetSupplyOfEveryPharmacyWhenGivenBigData() throws Exception {
        //given
        String path = "./to_test/bigData.txt";
        int need = 0;
        int got = 0;
        //when
        configurationIO.loadFromFile(path);
        VAM vam = new VAM(configurationIO.getPharmacyList(), configurationIO.getManufacturerList());
        vam.minimizeCost();
        for (Pharmacy pharmacy : vam.getPharmacyList()) {
            need += pharmacy.getNeed();
            got += pharmacy.getNeed() - pharmacy.leftToLoad();
        }
        //then
        assertEquals(need, got);
    }


    @Test
    public void shouldNotGoAboveManufacturersDailyProductionWhenGivenBigData() throws Exception {
        //given
        String path = "./to_test/bigData.txt";
        //when
        configurationIO.loadFromFile(path);
        VAM vam = new VAM(configurationIO.getPharmacyList(), configurationIO.getManufacturerList());
        vam.minimizeCost();
        //then
        for(Manufacturer manufacturer: vam.getManufacturerList()){
            assert manufacturer.leftToSell() >= 0;
        }
    }




}