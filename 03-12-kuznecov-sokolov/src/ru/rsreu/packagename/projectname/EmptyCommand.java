package ru.rsreu.packagename.projectname;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		/*
		 * � ������ ������ ��� ������� ��������� � ����������� ������������� �� ��������
		 * ����� ������
		 */
		String page = ConfigurationManager.getProperty("path.page.login");
		return page;
	}

}
