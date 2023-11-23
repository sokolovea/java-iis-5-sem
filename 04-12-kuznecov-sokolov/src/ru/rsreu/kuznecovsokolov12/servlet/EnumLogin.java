package ru.rsreu.kuznecovsokolov12.servlet;

public enum EnumLogin {
	ADMIN("admin"),
	MODERATOR("moderator"),
	EXPERT("expert"),
	CAPTAIN("captain"),
	USER("user"),
	NOUSER("nouser");
	
	private String title;
	
	EnumLogin(String title) {
       this.title = title;
	}
	
    @Override
    public String toString() {
        return this.title;
    }

}
