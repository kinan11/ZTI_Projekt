# Zdrowy student - README

Aplikacja Zdrowy student jest aplikacją wielowarstwową, która składa się z klienta i backendu. Poniżej znajdują się instrukcje, jak uruchomić aplikację za pomocą polecenia `docker compose up`.

## Wymagania

Aby móc uruchomić aplikację przy użyciu Docker Compose, musisz mieć zainstalowane następujące narzędzia:

- Docker: [link do instalacji](https://docs.docker.com/get-docker/)
- Docker Compose: [link do instalacji](https://docs.docker.com/compose/install/)

Upewnij się, że masz zainstalowane odpowiednie wersje tych narzędzi na swoim systemie.

## Uruchamianie aplikacji

1. Pobierz kod źródłowy projektu XYZ z repozytorium.

2. Przejdź do katalogu głównego projektu.

3. W pliku `docker-compose.yml` znajdującym się w katalogu głównym projektu, zmodyfikuj konfigurację portów, jeśli to konieczne. Domyślnie backend działa na porcie `8088`, a klient na porcie `80`. Jeśli te porty są już używane na twoim systemie, zmień je na wolne porty.

4. Otwórz terminal i wykonaj polecenie:

   ```shell
   docker compose up
   ```

   Komenda ta spowoduje, że Docker Compose pobierze odpowiednie obrazy i zbuduje kontenery dla klienta i backendu. Po zakończeniu procesu budowania aplikacja zostanie uruchomiona.

Po zakończeniu procesu uruchamiania aplikacji, otwórz przeglądarkę i wpisz adres http://localhost:80/. Zobaczysz interfejs klienta aplikacji Zdrowy student.

Jeśli chcesz uzyskać bezpośredni dostęp do backendu, otwórz nową kartę w przeglądarce i wpisz adres http://localhost:8088/. To jest endpoint backendu, do którego klient wysyła zapytania.

## Zakończenie działania aplikacji

Aby zatrzymać działanie aplikacji, przejdź do terminala, w którym zostało uruchomione polecenie

```shell
   docker compose down
```

Docker Compose zatrzyma działanie wszystkich kontenerów aplikacji.
