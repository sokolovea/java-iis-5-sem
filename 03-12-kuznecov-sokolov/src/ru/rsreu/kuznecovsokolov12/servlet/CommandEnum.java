package ru.rsreu.kuznecovsokolov12.servlet;

public enum CommandEnum {
	
	REPORT{
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
	};
	ActionCommand command;

	public ActionCommand getCurrentCommand() {
		return command;
	}
}
