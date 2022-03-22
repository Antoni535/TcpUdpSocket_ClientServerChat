# TcpUdpSocket_ClientServerChat
Aplikacja typu chat z wykorzystaniem socketów. Kleint może komunikować się z serwerem za pomocą TCP, UDP jak również Multicastu.
Serwer przyjmuje wiadomości od każdego klienta i rozsyła je do pozostałych (wraz z id/nickiem klienta). Serwer jest wielowątkowy – każde 
połączenie od klienta ma swój wątek.
