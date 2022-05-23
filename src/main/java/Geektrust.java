import service.LowestPriceRentalStrategy;
import service.VehicleRentalService;

import java.io.*;
import java.util.*;

public class Geektrust {

    public static void main(String[] args) throws IOException {

        String filePath = args[0];
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

//        BufferedWriter f_writer = new BufferedWriter(new FileWriter("/Users/klbairwa/Desktop/RentalOutput.txt"));
        VehicleRentalService vehicleRentalService = new VehicleRentalService();

        while (scanner.hasNext()) {
            String string = scanner.nextLine();
            String[] arr = string.split(" ");
            switch (arr[0]) {
                case "ADD_BRANCH":
                    List<String> vehicleList = new ArrayList<>();
                    Collections.addAll(vehicleList, arr[2].split(","));
                    System.out.println(vehicleRentalService.onboardBranch(arr[1].trim(), vehicleList));
//                    f_writer.write(String.valueOf( vehicleRentalService.onboardBranch(arr[1].trim(), vehicleList)));
//                    f_writer.write("\r\n");
                    break;
                case "ADD_VEHICLE":
                    System.out.println(vehicleRentalService.onboardVehicle(arr[1].trim(), arr[2].trim(), Double.valueOf(arr[4].trim()), arr[3].trim()));
//                    f_writer.write(String.valueOf(vehicleRentalService.onboardVehicle(arr[1].trim(), arr[2].trim(), Double.valueOf(arr[4].trim()), arr[3].trim())));
//                    f_writer.write("\r\n");
                    break;
                case "BOOK":
                    System.out.println(vehicleRentalService.bookVehicle(arr[1].trim(), arr[2].trim(), Long.parseLong(arr[3].trim()), Long.parseLong(arr[4].trim()), new LowestPriceRentalStrategy()));
//                    f_writer.write(String.valueOf(vehicleRentalService.bookVehicle(arr[1].trim(), arr[2].trim(), Long.parseLong(arr[3].trim()), Long.parseLong(arr[4].trim()), new LowestPriceRentalStrategy())));
//                    f_writer.write("\r\n");
                    break;
                case "DISPLAY_VEHICLES":
                    Map<Double, String> vehicles = vehicleRentalService.displayVehicles();
                    for (Map.Entry<Double, String> entry : vehicles.entrySet()) {
                       System.out.println(entry.getValue());
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + arr[0]);
            }

        }
    }

}

