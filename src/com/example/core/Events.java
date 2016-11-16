package com.example.core;

public class Events {

	public static class OnReceivedRefreshNewsShownEvent {
        public int msgType;

		public OnReceivedRefreshNewsShownEvent(int msgType) {
			this.msgType = msgType;
		}
        
    }
	
	public static class OnReceivedNewsEvent {
        public int msgType;

		public OnReceivedNewsEvent(int msgType) {
			this.msgType = msgType;
		}
        
    }
	
	public static class OnReceivedNoticesEvent {
        public int msgType;

		public OnReceivedNoticesEvent(int msgType) {
			this.msgType = msgType;
		}
        
    }

	public static class OnReceivedNoticeDetailEvent { 
		public String noticeDetail;

		public OnReceivedNoticeDetailEvent(String noticeDetail) {
			this.noticeDetail = noticeDetail;
		}
		
	}
	

	public static class OnReceivedCancelDialogEvent { }

	public static class OnReceivedCourseEvent { 
		public int count;
		public int page;
		public OnReceivedCourseEvent(int count, int page) {
			this.count = count;
			this.page = page;
		}
	}

	public static class OnReceivedStudentInfoEvent { 
		public boolean success;

		public OnReceivedStudentInfoEvent(boolean success) {
			this.success = success;
		}
	}

	public static class OnReceivedStudentSetEvent {  }

	public static class OnReceivedTeacherSetEvent {  }
	
	public static class OnReceivedSettingRefreshEvent { }
}
