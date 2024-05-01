package VotingRoom;

import CanBeVoted.*;

/**
 * Rozhranie VotingProcess definuje základné operácie pre proces hlasovania.
 * Obsahuje metódy, ktoré umožňujú účastníkom hlasovať, získavať prístupové údaje a kontrolovať stav hlasovania.
 */
public interface VotingProcess {
     /**
      * Umožňuje účastníkovi hlasovať za špecifický objekt, ktorý môže byť hlasovaný.
      *
      * @param canBeVoted Objekt, za ktorý sa hlasuje.
      */
     void vote(CanBeVoted canBeVoted);

     /**
      * Vráti používateľské meno účastníka hlasovania.
      *
      * @return Používateľské meno účastníka.
      */
     String getUsername();

     /**
      * Vráti heslo účastníka hlasovania.
      *
      * @return Heslo účastníka.
      */
     String getPassword();

     /**
      * Vráti informáciu, či už účastník hlasoval.
      *
      * @return True, ak účastník už hlasoval, inak false.
      */
     boolean getVoted();
}
