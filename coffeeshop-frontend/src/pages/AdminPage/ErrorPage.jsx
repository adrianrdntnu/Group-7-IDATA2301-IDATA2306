import { useTheme } from "@emotion/react";
import { Typography, Box } from "@mui/material";

function ErrorPage({ error }) {
  const theme = useTheme();

  return (
    <Box
      display="flex"
      flexDirection="column"
      justifyContent="center"
      alignItems="center"
      sx={{ minHeight: theme.boxSizes.navSection }}
    >
      <Typography variant="h2">{error}</Typography>
    </Box>
  );
}

export default ErrorPage;
