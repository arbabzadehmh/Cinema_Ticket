import com.example.cinema_test.model.entity.Seat;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//
//        int row = 19;
//
//        System.out.println(LETTERS.substring(row-1, row + 1).charAt(0));


        List<Seat> seats = new ArrayList<>();
        double ratio = 1;
        for (int i = 1; i <= 6; i++) {
            if (i <= 2) {
                ratio = 2;
            } else if (i > 2 && i < 5) {
                ratio = 1.5;
            } else {
                ratio = 1;
            }
            for (int j = 1; j <= 10; j++) {
                Seat seat =
                        Seat
                                .builder()
                                .rowNumber(i)
                                .seatNumber(j)
                                .label(null)
                                .priceRatio(ratio)
                                .status(true)
                                .deleted(false)
                                .description("test")
                                .build();

                seat.seatLabelMaker();
                seats.add(seat);
                System.out.printf(seat.toString() + "\n");

            }
        }







    }
}
