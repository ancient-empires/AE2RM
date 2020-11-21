src_zip := AEIIRM_src.zip
extract_dir := java

.PHONY: all
all: extract

.PHONY: extract
extract: $(src_zip)
	mkdir -vp $(extract_dir)/
	unzip -o $< -d $(extract_dir)/

.PHONY: clean
clean:
	-rm -rfv $(extract_dir)/
