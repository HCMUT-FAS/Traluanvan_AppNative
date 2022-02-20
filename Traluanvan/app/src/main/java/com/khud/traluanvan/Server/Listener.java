package com.khud.traluanvan.Server;

import java.util.List;

public interface Listener {
        void onDataReceived(List Respone);
        void onError(int error);
}
