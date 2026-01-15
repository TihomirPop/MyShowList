package hr.tpopovic.myshowlist.application.domain.model;

public enum Role {
    USER, ADMIN;

    public String toAuthority() {
        return "ROLE_" + this.name();
    }
}
