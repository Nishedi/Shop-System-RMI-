
Konrad Pempera
Celem projektu było oprogarowanie aplikacji obługujacej zamówienia
Aplikacja składa się z trzech podaplikacji (klient, serwer(repozytorium), pracownik(sprzedawca, zarządca)
Klient może: przeglądać zamówienia, składać zamówienie, edytować je, miec podgląd do ich statusu oraz zasubsykrybować tzn zdecydować czy chce automatycznie dostawać powiadomienia odnośnie zmiany stanu zamówienia
Pracownik edytuje statusy zamówień oraz ma podgląd do wszystkich zamówień
Server umożliwia dodawanie oraz usuwanie użytkowników oraz produktów jednoczesnie umożliwia monitorowanie zamówień i ich statusów
Aplikacja została skompilowana w jdk 11 komendą javac -p gadgets.jar -encoding UTF8 -d bin src\clientcontroller\ClientController.java src\clientmain\Client.java
      src\clientmain\IStatusListenerConnection.java src\clientview\ClientFrame.java src\clientview\ClientTableRemote.java src\clientview\LoginPanel.java
      src\clientview\ScrollingPanel.java src\clientview\StatusPanel.java src\clientview\Table.java src\sellercontroller\SellerController.java src\sellermain\Seller.java
      src\sellersview\ExtendedTable.java src\sellersview\SellerFrame.java src\sellersview\SellerTable.java src\sellersview\SellerTableRemote.java
      src\servercontroller\ServerController.java src\servermain\ReadSave.java src\servermain\Server.java src\servermodel\AccountMenager.java src\servermodel\ProductMenager.java
      src\servermodel\SubmittedOrderMenager.java src\serverview\ClientTable.java src\serverview\ProductTable.java src\serverview\ServerFrame.java src\module-info.java
      src\servermodel\MyPolicy.java src\servermodel\MyPermissionCollection.java
Plik jar został stworzony za pomocą komendy jar -cfv lab07_pop.jar -C bin . src
Aplikacja polega na innym pliku typu jar, który musi być uwzględniony przy kompilacji oraz uruchamianiu, do kompilacji słuzy flaga -p gadgets.jar
W celu uruchomienia aplikacji z zależnością należy na początku uruchomić komendę set CLASSPATH=gadgets.jar nastepnie
java -p gadgets.jar;lab07_pop.jar -m lab07.pop/servermain.Server dla serwera
java -p gadgets.jar;lab07_pop.jar -m lab07.pop/clientmain.Client host port dla klienta
java -p gadgets.jar;lab07_pop.jar -m lab07.pop/sellermain.Seller host port dla sprzedawcy
W celu uruchomienia aplikacji na odpowiednim porcie i hoscie nalezy wpisać odpowiednie wartości w pola host i port przy wywołaniu
