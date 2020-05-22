# Soluzioni esami tecweb

Ognuno si crei il proprio branch e carichi lì tutte le soluzioni, idealmente con la data dell'appello e il numero dell'esercizio nel nome del progetto/cartella

**Occhio alle credenziali in quelli con la persistenza**

## Per contribuire

- Dalla home, tasto verde `Clone or download` e copiare il link
- Da terminale fare `git clone [link_copiato]`, o da Git GUI fare clona repository e inserire il link
- Entrare nella cartella generata esami_tecweb e creare il proprio branch con `git checkout -b [proprio_nome]`, ad esempio `git checkout -b mariorossi`
- A questo punto siete nel vostro branch e potete caricare quello che volete, in particolare mettete le cartelle dei vostri progetti nella cartella `esami_tecweb` e per caricare

```
git add .
git commit -m "Breve messaggio per scrivere cosa aggiungo"
git push
```

**NB:** al primo push che fate sul vostro branch vi dirà che non esiste un upstream, in quel caso basta fare un `git push -u [nome_vostro_branch]`. Per tutte le volte successive basta `git push`.
