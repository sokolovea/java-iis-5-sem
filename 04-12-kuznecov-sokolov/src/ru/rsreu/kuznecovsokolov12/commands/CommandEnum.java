package ru.rsreu.kuznecovsokolov12.commands;

public enum CommandEnum {
	MENU {
		{
			this.command = new MenuCommand();
		}
	},
	
	REPORT {
		{
			this.command = new ReportCommand();
		}
	},
	
	LOGIN {
		{
			this.command = new LoginCommand();
		}
	},
	LOGOUT {
		{
			this.command = new LogoutCommand();
		}
	},
	DATABASE {
		{
			this.command = new DatabaseCommand();
		}
	};
	ActionCommand command;

	public ActionCommand getCurrentCommand() {
		return command;
	}
}
