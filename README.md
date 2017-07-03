## 1. Zainstalowanie i stworzenie bazy danych.

Pobieramy i instalujemy MongoDB
+ [MongoDB](https://www.mongodb.com/download-center#community)

Tworzymy katalog `data` dla danych bazy danych MongoDB i podkatalogi `db` i `log` np.:
```
C:\data
C:\data\db
C:\data\log
```

Tworzymy plik konfiguracyjny mongod.cfg i w zawartości wskazujemy na stworzony katalog `data` np.:
```
systemLog:
    destination: file
    path: c:\data\log\mongod.log
storage:
    dbPath: c:\data\db
```

Stworzony plik konfiguracyjny przenosimy do katalogu z zainstalowanym MongoDB i dodajemy ścieżkę path do tego katalogu np.:
```
C:\Program Files\MongoDB\Server\3.4\bin
```

Otwieramy wiersz poleceń i uruchamiamy serwer MongoDB wpisując:
```bash
mongod
```

Otwieramy koljne okno wiersza poleceń i uruchamiamy klienta MongoDB wpisując:
```bash
mongo
```

W oknie z klientem tworzymy bazę danych wpisując:
```bash
use intelligent_face_recognizer
```

W oknie z klientem tworzymy kolekcję wpisując:
```bash
db.createCollection("faces")
```

## 2. Uruchomienie aplikacji.

Przed włączeniem aplikacji należy pamiętać by serwer MongoDB był włączony.

Uruchamiamy projekt w dowolnym IDE wspierającym Maven, preferowany Intellij.

