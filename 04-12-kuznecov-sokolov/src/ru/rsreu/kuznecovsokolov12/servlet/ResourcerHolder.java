package ru.rsreu.kuznecovsokolov12.servlet;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

public interface ResourcerHolder {

	static final Resourcer resourser = ProjectResourcer.getInstance();
	
}
