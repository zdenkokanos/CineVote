package VotingRoom;

/**
 * Vlastná výnimka používaná na signalizáciu situácie, kedy užívateľ nevyplní povinné vstupné polia vo formulároch.
 * Táto výnimka pomáha lepšie zvládať chyby spojené s užívateľským vstupom v aplikácii.
 */
public class BlankInputException extends Exception {

    /**
     * Konštruktor pre BlankInputException, ktorý prijíma správu popisujúcu chybu.
     * Táto správa sa potom môže zobraziť užívateľovi, aby vedel, že musí vyplniť všetky požadované polia.
     *
     * @param message Správa, ktorá sa zobrazí, keď je výnimka zachytená a spracovaná.
     */
    public BlankInputException(String message) {
        super(message);
    }
}
