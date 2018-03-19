package com.cpssoft.dev.zweb.action;

public class IndexAction extends BaseAction {

	@Action(path = "index")
	public void index() {

		writeResponsePage();
	}
}
