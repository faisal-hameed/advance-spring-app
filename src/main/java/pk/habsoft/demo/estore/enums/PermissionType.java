package pk.habsoft.demo.estore.enums;

import lombok.Getter;

@Getter
public enum PermissionType {

	USER_MENU(1L, "Users", "/admin"), //
	REPORT_SHOW_OOS_ITEMS(2L, "Out of Stock Items", "/admin/reports"), //
	REPORT_SHOW_TODAY_SALES(4L, "Daily Sales", "/admin/report");

	private long id;
	private String label;
	private String menuPath;

	private PermissionType(long id, String label, String menuPath) {
		this.id = id;
		this.label = label;
		this.menuPath = menuPath;
	}

}
