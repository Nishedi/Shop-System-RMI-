package sellermain;

import sellercontroller.SellerController;

public class Seller {
    public static void main(String[] args) {
        if(args.length>0) {
            SellerController sellerController = new SellerController(args[0], args[1]);
        }else{
            SellerController sellerController = new SellerController("localhost", "3000");
        }
    }

}
