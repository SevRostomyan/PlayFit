package playfit.se.members.enums;

public enum UserRole {
    MEMBER, //Förenings-/Klubbmedlem
    STUDENT, //Idrottare
    APP_ADMIN, //Admin för applikationen
    ORG_ADMIN, //Admin för ett specifikt organisation
    ORG_CONTACT_PERSON, //Målsman ?? menar du att det ska vara kontakt person for organisation eller Guardian?
    TRAINER, //Tränare
    GUARDIAN, //Representant till barnen
    BOARD_MEMBER, //Styrelseledamot till organisationen
    CHAIRMAN  //Styrelseordförande
    // om user är med annan familjemedlem eller inte
}
