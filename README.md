![Logo](https://user-images.githubusercontent.com/90064221/236351852-56cc9043-76d0-4f1a-ac88-9e2a5d71b42d.png)
# CraftGPT

CraftGPT is a lightweight plugin that allows you to integrate an AI-powered chatbot into your Minecraft server. With this plugin, your players can interact with an AI chatbot that uses OpenAI's API to generate responses based on the conversation.

## Features
- Lightweight plugin with minimal impact on server performance
- Easy integration into your Spigot server
- Command-based interface for generating responses
- Cooldown timer to prevent spamming
- Flexible configuration

## Installation
To install CraftGPT, you will first need to sign up for an OpenAI API key, which is required to use the API. Here's how to get your API key:
1. Go to the [OpenAI website](https://openai.com) and create an account.
2. Once you have created an account, go to the [API keys page](https://platform.openai.com/account/api-keys) to generate an API key.
3. Copy the API key to your clipboard.

Once you have your OpenAI API key, you can install CraftGPT:
1. Download the plugin from SpigotMC and place it in your server's plugins directory.
2. Open the `plugins/CraftGPT/config.yml` file in a text editor.
3. Paste your OpenAI API key into the `token` field in the configuration file.
4. Save the configuration file and restart your server.

That's it! CraftGPT is now installed and ready to use.

## Configuration
CraftGPT is highly configurable via the `config.yml` file. Here are the available configuration options:
- `cooldown`: The cooldown time in milliseconds between `/gpt` commands.
- `show-errors`: Whether to display error messages or not.
- `messages`: Customizable messages that are displayed to players in various situations, including cooldown messages and error messages. These messages now support formatting! Check out [Formatting](https://docs.advntr.dev/minimessage/format.html) for info.
- `chatgpt`: Various ChatGPT settings.

By default, CraftGPT uses the `gpt-3.5-turbo` model, which is cheap, powerful, and designed specifically for conversations, and all the other settings set to OpenAI's default. However, you can change anything at any time in the `chatgpt` section.

## Usage
CraftGPT provides three commands:
- `/craftgpt`: Reload the configuration. Players must have the `craftgpt.reload` permission to use this command.
- `/gpt <text>`: Generates a response to the given text. Players must have the `craftgpt.gpt` permission to use this command. This command has a cooldown timer to prevent spamming. If a player attempts to use the command while it is on cooldown, they will receive a message indicating how long they must wait before using the command again.
- `/newchat`: Clears the chat dialog. Players must have the `craftgpt.newchat` permission to use this command.

## Permissions
CraftGPT provides the following permission nodes:
- `craftgpt.reload`: Allows players to use the `/craftgpt` command.
- `craftgpt.gpt`: Allows players to use the `/gpt` command.
- `craftgpt.newchat`: Allows players to use the `/newchat` command.
- `craftgpt.use`: Grants both of the above. This permission is granted by default.

## Support
If you have any issues with CraftGPT or need help configuring it, please visit the [CraftGPT page on SpigotMC](https://www.spigotmc.org/resources/craftgpt.109639) for support.

## Disclaimer:
CraftGPT is not affiliated with or endorsed by OpenAI. The plugin uses OpenAI's API, but is not officially supported by OpenAI.

## Development Status
CraftGPT is currently in early development. While the plugin has been tested and is functional, it is not yet feature-complete and may undergo significant changes in the future.
If you are interested in seeing further development of CraftGPT, please feel free to reach out to me, as **I will not update it unless someone is actually using it**. Your feedback and suggestions are always welcome, and will help me to improve the plugin over time.
