# Selenium Grid Server

Requirements:
1. Java (if it's installed correctly, you must run java command from any terminal) - https://www.java.com/download/ie_manual.jsp
2. Google Chrome 112.0.5615.X (or any latest version)

How to start:
1. Run `start.bat`
3. Check `localhost:4444/ui`

If it worked, you should see:
![hubPage](hub.png)

## Important:
- The grid server is configured for your CPU thread number of chromedrivers instances
- If your test fails, the chromedriver is not killed, you must kill it using task manager, or `kill-chromedrivers.bat` script
- If your test executes without errors, the chromedriver will be killed by itself